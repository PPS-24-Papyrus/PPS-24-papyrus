package papyrus.logic.utility.prolog


import alice.tuprolog.*

object Scala2Prolog:
  given Conversion[String, Term] = Term.createTerm(_)
  given Conversion[Seq[_], Term] = _.mkString("[", ",", "]")

  def extractTerm(t: Term, i: Integer): Term =
    t.asInstanceOf[Struct].getArg(i).getTerm

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
