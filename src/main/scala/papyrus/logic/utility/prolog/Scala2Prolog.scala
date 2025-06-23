package papyrus.logic.utility.prolog


import alice.tuprolog.*

/** Utility object for working with tuProlog from Scala */
object Scala2Prolog:

  /** Converts a string into a Prolog term */
  given Conversion[String, Term] = Term.createTerm(_)

  /** Converts a Scala sequence into a Prolog list term string */
  given Conversion[Seq[_], Term] = _.mkString("[", ",", "]")

  /** Extracts the i-th argument from a compound Prolog term */
  def extractTerm(t: Term, i: Integer): Term =
    t.asInstanceOf[Struct].getArg(i).getTerm

  /** Creates a Prolog engine with the given clauses
   *
   * Returns a function that takes a goal and produces a lazy stream of solutions
   */
  def mkPrologEngine(clauses: String*): Term => LazyList[Term] =
    val engine = Prolog()
    engine.setTheory(Theory(clauses.mkString(" ")))
    goal =>
      new Iterable[Term] {
        override def iterator: Iterator[Term] = new Iterator[Term] {
          var current: SolveInfo = engine.solve(goal)
          var hasNextCached: Boolean = current.isSuccess

          override def hasNext: Boolean = hasNextCached

          override def next(): Term =
            if (!hasNextCached)
              throw new NoSuchElementException("No more Prolog solutions")
            val solution = current.getSolution
            if (current.hasOpenAlternatives)
              current = engine.solveNext()
              hasNextCached = current.isSuccess
            else
              hasNextCached = false
            solution
        }
      }.to(LazyList)
