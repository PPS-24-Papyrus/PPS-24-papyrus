package papyrus.logic.layerElement.captionElement

import papyrus.logic.Renderer.Text.*
import papyrus.logic.styleObjects.TableStyle
import papyrus.logic.utility.prolog.Scala2Prolog.{*, given}
import alice.tuprolog.{Struct, Term, Int as PInt, Var}


trait  Table[T] extends CaptionElement:
  def rows: List[Row[T]]
  def tableStyle: TableStyle
  def renderFunction: T => MainText

trait Row[T]:
  def cells: List[Cell[T]]
  def render(renderFunction: T => MainText): MainText

trait Cell[T]:
  def content: T
  def head: Boolean
  def colspan: Int
  def rowspan: Int
  def render(renderFunction: T => MainText): MainText


object Table:
  def apply[T](
               caption: Option[String],
               rows: List[Row[T]],
               tableStyle: TableStyle,
               renderFunction: T => MainText 
             ): Table[T] = TableImpl(caption, rows, tableStyle, renderFunction)

  private case class TableImpl[T](
                              override val caption: Option[String],
                              override val rows: List[Row[T]],
                              override val tableStyle: TableStyle,
                              override val renderFunction: T => MainText
                            ) extends Table[T]:

    override def render: MainText =
      validateColspanConsistency(rows) match
        case Right(_) =>
          val bodyRows = rows.map(_.render(renderFunction)).mkString
          val captionString = caption.map(c => s"<caption>$c</caption>").getOrElse("")

          s"""<div class="${tableStyle.tag}">\n<table>\n$captionString\n<tbody>\n$bodyRows</tbody>\n</table>\n</div>""".stripMargin.toMainText

        case Left(errorHtml) =>
          s"""<div class="${tableStyle.tag}">
             |  <p style="color:red;">$errorHtml</p>
             |</div>
             |""".stripMargin.toMainText

    override def renderStyle: StyleText =
      tableStyle.renderStyle

    private def validateColspanConsistency(rows: List[Row[_]]): Either[String, Unit] =
      val data = rows.map(_.cells.map(_.colspan))
      val termString = data.map(_.mkString("[", ",", "]")).mkString("[", ",", "]")
      val prologTerm = Term.createTerm(termString)

      val goal = new Struct("validate_colspan_consistency", prologTerm)
      val result = engine(goal).headOption
    
      result match
        case Some(_) => Right(())
        case None =>
          val colCounts = data.zipWithIndex.map { case (row, i) => (i, row.sum) }
          val details = colCounts.map((i, c) => s"Row $i → $c columns").mkString("<br>")
          Left(s"<div style='color:red'><strong>Table structure error:</strong><br>$details</div>")


  val engine = mkPrologEngine(
    """
      sum_list([], 0).
      sum_list([H|T], Sum) :- sum_list(T, Rest), Sum is H + Rest.
  
      row_colspans([], []).
      row_colspans([Row|Rest], [Sum|Sums]) :- sum_list(Row, Sum), row_colspans(Rest, Sums).
  
      all_equal([]).
      all_equal([_]).
      all_equal([X, X | Rest]) :- all_equal([X | Rest]).
  
      validate_colspan_consistency(Rows) :-
        row_colspans(Rows, Sums),
        all_equal(Sums).
      """
  )


object Row:
  def apply[T](cells: List[Cell[T]]): Row[T] = RowImpl(cells)

  private class RowImpl[T](override val cells: List[Cell[T]]) extends Row[T]:
    override def render(renderFunction: T => MainText): MainText =
      val cellStrings = cells.map(_.render(renderFunction)).mkString
      s"<tr>\n$cellStrings</tr>\n".toMainText

object Cell:
  def apply[T](content: T, head: Boolean = false, colspan: Int = 1, rowspan: Int = 1): Cell[T] = CellImpl(content, head, colspan, rowspan)

  private class CellImpl[T](override val content: T,
                         override val head: Boolean,
                         override val colspan: Int,
                         override val rowspan: Int) extends Cell[T]:
    override def render(renderFunction: T => MainText): MainText =
      val tag = if head then "th" else "td"
      s"<$tag colspan='$colspan' rowspan='$rowspan'>${renderFunction(content)}</$tag>\n".toMainText