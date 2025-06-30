package papyrus.logic.layerElement.captionElement

import papyrus.logic.Renderer.Text.*
import papyrus.logic.styleObjects.TableStyle
import alice.tuprolog.{Struct, Term, Int as PInt, Var}
import papyrus.utility.prolog.Scala2Prolog.mkPrologEngine

/** A table with rows and a caption, rendered using a function */
trait Table[T] extends CaptionElement:
  /** The list of table rows */
  def rows: List[Row[T]]

  /** The style applied to the table */
  def tableStyle: TableStyle

  /** Function that renders a cell's content as MainText */
  def renderFunction: T => MainText

/** A row of cells in a table */
trait Row[T]:
  /** The list of cells in this row */
  def cells: List[Cell[T]]

  /** Renders the row using the provided cell rendering function */
  def render(renderFunction: T => MainText): MainText

/** A single cell in a row */
trait Cell[T]:
  /** The content stored in the cell */
  def content: T

  /** Whether the cell is a header cell (`<th>`) */
  def head: Boolean

  /** Number of columns this cell spans */
  def colspan: Int

  /** Number of rows this cell spans */
  def rowspan: Int

  /** Renders the cell using the provided content rendering function */
  def render(renderFunction: T => MainText): MainText


object Table:

  /** Creates a table from caption, rows, style and rendering function */
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
      val termString: String = rows
                                 .map(_.cells.map(c => intToPeano(c.colspan))
                                   .mkString("[", ",", "]"))
                                 .mkString("[", ",", "]")

      val prologTerm = Term.createTerm(termString)

      val goal = new Struct("validate_colspan_consistency", prologTerm)
      val result = engine(goal).headOption
    
      result match
        case Some(_) => Right(())
        case None =>
          val colCounts = rows.map(_.cells.map(_.colspan)).zipWithIndex.map { case (row, i) => (i, row.sum) }
          val details = colCounts.map((i, c) => s"Row $i → $c columns").mkString("<br>")
          Left(s"<div style='color:red'><strong>Table structure error:</strong><br>$details</div>")

    private def intToPeano(n: Int): String =
      if n <= 0 then "zero"
      else "s(" * n + "zero" + ")" * n

    private val engine = mkPrologEngine(
      """
        sum(zero, N, N).
        sum(s(M), N, s(R)) :- sum(M, N, R).

        sum_list([], zero).
        sum_list([H|T], Sum) :-
            sum_list(T, Rest),
            sum(H, Rest, Sum).

        row_colspans([], []).
        row_colspans([Row|Rest], [Sum|Sums]) :-
            sum_list(Row, Sum),
            row_colspans(Rest, Sums).

        all_equal([]).
        all_equal([_]).
        all_equal([X, X | Rest]) :- all_equal([X | Rest]).

        validate_colspan_consistency(Rows) :-
            row_colspans(Rows, Sums),
            all_equal(Sums).
        """
    )

object Row:

  /** Create a row from a list of cells */
  def apply[T](cells: List[Cell[T]]): Row[T] = RowImpl(cells)

  private class RowImpl[T](override val cells: List[Cell[T]]) extends Row[T]:
    override def render(renderFunction: T => MainText): MainText =
      val cellStrings = cells.map(_.render(renderFunction)).mkString
      s"<tr>\n$cellStrings</tr>\n".toMainText

object Cell:

  /** Create a cell with content, colspan, rowspan and head flag */
  def apply[T](content: T, head: Boolean = false, colspan: Int = 1, rowspan: Int = 1): Cell[T] = CellImpl(content, head, colspan, rowspan)

  private class CellImpl[T](override val content: T,
                         override val head: Boolean,
                         override val colspan: Int,
                         override val rowspan: Int) extends Cell[T]:
    override def render(renderFunction: T => MainText): MainText =
      val tag = if head then "th" else "td"
      s"<$tag colspan='$colspan' rowspan='$rowspan'>${renderFunction(content)}</$tag>\n".toMainText