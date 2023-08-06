package bd.kdtree.classes

import scala.annotation.targetName

/**
 * Created by Bomen Derick.
 */

/**
 * Represent a point in a K-dimensional tree
 *
 * @param value
 *   A sequence of numbers used to represent a point in a tree
 * @tparam T
 *  The type of numbers used in a tree
 */
case class Point[+T](value: Seq[T])

case object Point {
  def apply[T](): Point[T] = Point(Seq())
  @targetName("sequence synctatic sugar")
  def apply[T](point: T*): Point[T] = Point(point.toSeq)
}
