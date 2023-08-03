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
