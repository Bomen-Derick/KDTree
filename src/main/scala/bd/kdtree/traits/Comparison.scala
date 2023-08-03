package bd.kdtree.traits
/**
 * Created by Bomen Derick.
 */
trait Comparison[T] {
  /**
   * Determine the ordering between x and y in that order and in ascending order
   *
   * @param x
   * @param y
   * @return
   * return true if x is less than y false otherwise
   */
  def isLessThan(x: T, y: T): Boolean
}

/**
 * A given instance that determines the ordering between two Integer values and returns a Boolean
 */
given intComparison: Comparison[Int] = (x: Int, y: Int) => x < y

/**
 * A given instance that determines the ordering two Double values and returns a Boolean
 */
given doubleComparison: Comparison[Double] = (x: Double, y: Double) => x < y

/**
 * A given instance that determines the ordering between an Integer and a Double and returns a Boolean
 */
given doubleAndIntComparison: Comparison[Int | Double] = new Comparison[Int | Double]:
  override def isLessThan(x: Int | Double, y: Int | Double): Boolean = (x, y) match
    case (a: Int, b: Int) => intComparison.isLessThan(a, b)
    case (a: Double, b: Double) => doubleComparison.isLessThan(a, b)
    case (a: Int, b: Double) => a < b
    case (a: Double, b: Int) => a < b

