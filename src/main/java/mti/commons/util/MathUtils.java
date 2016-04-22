package mti.commons.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.vecmath.Point2d;
import javax.vecmath.Point3d;
import javax.vecmath.Vector2d;
import javax.vecmath.Vector3d;

import org.apache.commons.lang.time.DateUtils;

public class MathUtils
{
	public static double lowestDouble = -8.988465674311579E307;

	public static double highestDouble = 8.988465674311579E307;

	public static int clamp(
		final int value,
		final int min,
		final int max ) {
		return (Math.min(
			Math.max(
				value,
				min),
			max));
	}

	public static long clamp(
		final long value,
		final long min,
		final long max ) {
		return (Math.min(
			Math.max(
				value,
				min),
			max));
	}

	public static float clamp(
		final float value,
		final float min,
		final float max ) {
		return (Math.min(
			Math.max(
				value,
				min),
			max));
	}

	public static double clamp(
		final double value,
		final double min,
		final double max ) {
		return (Math.min(
			Math.max(
				value,
				min),
			max));
	}

	public static double getNormalizedAngle(
		double value ) {
		while (value < 0) {
			value += 360;
		}
		value = value % 360;

		return value;
	}

	public static boolean rangesOverlapInclusive(
		final double min1,
		final double max1,
		final double min2,
		final double max2 ) {
		if (inRangeInclusive(
			min1,
			min2,
			max2)) {
			return true;
		}
		else if (inRangeInclusive(
			max1,
			min2,
			max2)) {
			return true;
		}
		else if (inRangeInclusive(
			min2,
			min1,
			max1)) {
			return true;
		}
		else if (inRangeInclusive(
			max2,
			min1,
			max1)) {
			return true;
		}
		else {
			return false;
		}
	}

	public static boolean inRangeInclusive(
		final double value,
		final double min,
		final double max ) {
		return ((value >= min) && (value <= max));
	}

	public static boolean inRangeExclusive(
		final double value,
		final double min,
		final double max ) {
		return ((value > min) && (value < max));
	}

	public static boolean inRangeInclusive(
		final int value,
		final int min,
		final int max ) {
		return ((value >= min) && (value <= max));
	}

	public static boolean inRangeExclusive(
		final int value,
		final int min,
		final int max ) {
		return ((value > min) && (value < max));
	}

	static public double[] incorporateValueInBounds(
		double[] bounds,
		final double val ) {
		if (bounds == null) {
			bounds = new double[2];
			bounds[0] = val;
			bounds[1] = val;
		}
		else {
			if (val < bounds[0]) {
				bounds[0] = val;
			}

			if (val > bounds[1]) {
				bounds[1] = val;
			}
		}

		return bounds;
	}

	static public Point2d[] get2DBounds(
		final ArrayList<Point2d> pts ) {
		double[] xBounds = null;
		double[] yBounds = null;

		for (final Point2d pt : pts) {
			xBounds = incorporateValueInBounds(
				xBounds,
				pt.x);

			yBounds = incorporateValueInBounds(
				yBounds,
				pt.y);
		}

		Point2d[] bounds = null;

		if (xBounds != null) {
			bounds = new Point2d[2];

			for (int i = 0; i < 2; i++) {
				bounds[i] = new Point2d(
					xBounds[i],
					yBounds[i]);
			}
		}

		return bounds;
	}

	static public Point2d[] incorporateBoundsInBounds(
		Point2d[] comprehensiveBounds,
		final Point2d[] newBounds ) {
		if (newBounds == null) {
			return comprehensiveBounds;
		}

		if (comprehensiveBounds == null) {
			if (newBounds != null) {
				comprehensiveBounds = new Point2d[2];

				if (newBounds[0] != null) {
					comprehensiveBounds[0] = new Point2d(
						newBounds[0]);
				}

				if (newBounds[1] != null) {
					comprehensiveBounds[1] = new Point2d(
						newBounds[1]);
				}
			}
		}
		else {
			incorporatePointInBounds(
				comprehensiveBounds,
				newBounds[0]);
			incorporatePointInBounds(
				comprehensiveBounds,
				newBounds[1]);
		}

		return comprehensiveBounds;
	}

	static public Point2d[] incorporatePointInBounds(
		Point2d[] bounds,
		final Point2d pt ) {
		if (bounds == null) {
			bounds = new Point2d[2];
			bounds[0] = new Point2d(
				pt);
			bounds[1] = new Point2d(
				pt);
		}
		else {
			if (pt.x < bounds[0].x) {
				bounds[0].x = pt.x;
			}

			if (pt.y < bounds[0].y) {
				bounds[0].y = pt.y;
			}

			if (pt.x > bounds[1].x) {
				bounds[1].x = pt.x;
			}

			if (pt.y > bounds[1].y) {
				bounds[1].y = pt.y;
			}
		}

		return bounds;
	}

	static public Point3d getPlaneLineIntersection(
		final Point3d pt1,
		final Point3d pt2,
		final Point3d planePt,
		final Vector3d planeNormal ) {
		final double u = planeNormal.dot(
			new Vector3d(
				planePt.x - pt1.x,
				planePt.y - pt1.y,
				planePt.z - pt1.z))
			/ planeNormal.dot(
				new Vector3d(
					pt2.x - pt1.x,
					pt2.y - pt1.y,
					pt2.z - pt1.z));
		if ((u <= 0) || (u >= 1)) {
			return null;
		}
		return new Point3d(
			pt1.x + (u * (pt2.x - pt1.x)),
			pt1.y + (u * (pt2.y - pt1.y)),
			pt1.z + (u * (pt2.z - pt1.z)));
	}

	static public ArrayList<Double> getIncrementalValues(
		final double rangeMin,
		final double rangeMax,
		final int numValues ) {
		final double snap = MathUtils.getBestIncrementForRange(
			(rangeMax - rangeMin),
			numValues);
		final ArrayList<Double> valueList = new ArrayList<Double>();
		final double startValue = MathUtils.getBestValueForIncrement(
			rangeMin,
			snap);
		final double gap = (rangeMax - rangeMin) - (snap * numValues);
		int actualCount = numValues;

		if (gap > 0) {
			actualCount += (int) (gap / snap) + 1;
		}

		for (int i = 0; i < actualCount; i++) {
			final double nextValue = startValue + (i * snap);
			if (nextValue > rangeMax) {
				break;
			}

			valueList.add(
				new Double(
					nextValue));
		}

		return valueList;
	}

	static public double getBestValueForIncrement(
		final double value,
		final double increment ) {
		double adjust = value % increment;
		// if ( adjust < increment / 2 ) {
		// adjust = -adjust;
		// }
		// else {
		// adjust = increment - adjust;
		// }
		//
		if (adjust < 0) {
			adjust += increment;
		}
		return (value - adjust);
	}

	static public double getBestIncrementForRange(
		final double range,
		final int numIncrements ) {
		if (numIncrements <= 0) {
			return range;
		}

		double snap = (Math.abs(
			range) / numIncrements);

		if (snap < 0.1) {
			double round = 0.05;
			boolean swap = true;
			while (snap < (round / 2)) {
				if (swap) {
					round /= 5;
				}
				else {
					round /= 2;
				}
				swap = !swap;
			}
			snap = round;
		}
		else if (snap < 0.25) {
			snap = 0.1;
		}
		else if (snap < 0.5) {
			snap = 0.25;
		}
		else if (snap < 1.0) {
			snap = 0.5;
		}
		else if (snap < 2.0) {
			snap = 1.0;
		}
		else if (snap < 5.0) {
			snap = 2.0;
		}
		else if (snap < 10.0) {
			snap = 5.0;
		}
		else if (snap < 25.0) {
			snap = 10.0;
		}
		else if (snap < 50.0) {
			snap = 25.0;
		}
		else if (snap < 100.0) {
			snap = 50.0;
		}
		else if (snap < 200.0) {
			snap = 100.0;
		}
		else if (snap < 500.0) {
			snap = 200.0;
		}
		else if (snap < 1000.0) {
			snap = 500.0;
		}
		else if (snap <= 2000.0) {
			snap = 1000.0;
		}
		else {
			double round = 1000.0;
			boolean swap = true;
			while (snap > (round * 2)) {
				if (swap) {
					round *= 5;
				}
				else {
					round *= 2;
				}
				swap = !swap;
			}
			snap = round;
			// for (int i = 30; i > 10; i--) {
			// round = Math.pow(2, i);
			// if ( snap > round ) {
			// round = Math.pow(2, i+1);
			// break;
			// }
			// }
			// snap += (round - (snap % round));
		}
		return snap;
	}

	static public double getBestDateIncrementForRange(
		final double range,
		final int numIncrements ) {

		double snap = (range / numIncrements);
		final double sec = DateUtils.MILLIS_PER_SECOND;
		final double min = DateUtils.MILLIS_PER_MINUTE;
		final double hour = DateUtils.MILLIS_PER_HOUR;

		if (snap < 1000) {
			return getBestIncrementForRange(
				range,
				numIncrements);
		}
		else if (snap < (5 * sec)) {
			return sec;
		}
		else if (snap < (15 * sec)) {
			return 5 * sec;
		}
		else if (snap < min) {
			return 15 * sec;
		}
		else if (snap < (5 * min)) {
			return min;
		}
		else if (snap < (15 * min)) {
			return 5 * min;
		}
		else if (snap < hour) {
			return 15 * min;
		}
		else {
			double round = hour;
			int count = 1;
			// so it will hit a 1-day increment in its progression
			while (snap > (round * 2)) {
				if ((count % 4) == 0) {
					round *= 3;
				}
				else {
					round *= 2;
				}
				count += 1;
			}
			snap = round;
		}

		return snap;
	}

	static public Point2d rotateXYPlane(
		final double x,
		final double y,
		final double radianAngle ) {
		final double cos_theta = Math.cos(
			radianAngle);
		final double sin_theta = Math.sin(
			radianAngle);
		final Point2d retVal = new Point2d();
		retVal.x = (x * cos_theta) + (y * sin_theta);
		retVal.y = (y * cos_theta) - (x * sin_theta);
		return retVal;
	}

	static public List<Point2d> rotateXYPlane(
		final List<Point2d> pts,
		final double radianAngle ) {
		final List<Point2d> retVal = new ArrayList<Point2d>();
		for (final Point2d pt : pts) {
			retVal.add(
				rotateXYPlane(
					pt.x,
					pt.y,
					radianAngle));
		}
		return retVal;
	}

	static public Point2d rotateXYPlane(
		final Point2d origin,
		final double x,
		final double y,
		final double radianAngle ) {
		final double cos_theta = Math.cos(
			radianAngle);
		final double sin_theta = Math.sin(
			radianAngle);
		final Point2d retVal = new Point2d();
		retVal.x = (x * cos_theta) + (y * sin_theta) + origin.x;
		retVal.y = ((y * cos_theta) - (x * sin_theta)) + origin.y;
		return retVal;
	}

	public static final List<Point2d> getCirclePoints(
		final double xCenter,
		final double yCenter,
		final double radius ) {
		int x = 0;
		double y = radius;
		double p = (5 - (radius * 4)) / 4;
		final List<Point2d> lowerPts = new ArrayList<Point2d>();
		final List<Point2d> upperPts = new ArrayList<Point2d>();
		final List<Point2d> leftPts = new ArrayList<Point2d>();
		final List<Point2d> rightPts = new ArrayList<Point2d>();
		circlePoints(
			xCenter,
			yCenter,
			x,
			y,
			lowerPts,
			upperPts,
			leftPts,
			rightPts);
		while (x < y) {
			x++;
			if (p < 0) {
				p += (2 * x) + 1;
			}
			else {
				y--;
				p += (2 * (x - y)) + 1;
			}
			circlePoints(
				xCenter,
				yCenter,
				x,
				y,
				lowerPts,
				upperPts,
				leftPts,
				rightPts);
		}
		final List<Point2d> allPts = new ArrayList<Point2d>();
		allPts.addAll(
			leftPts);
		allPts.addAll(
			upperPts);
		allPts.addAll(
			rightPts);
		allPts.addAll(
			lowerPts);
		return allPts;
	}

	private static final void circlePoints(
		final double cx,
		final double cy,
		final double x,
		final double y,
		final List<Point2d> lowerPts,
		final List<Point2d> upperPts,
		final List<Point2d> leftPts,
		final List<Point2d> rightPts ) {

		if (x == 0) {
			upperPts.add(
				new Point2d(
					cx,
					cy + y));
			lowerPts.add(
				new Point2d(
					cx,
					cy - y));
			rightPts.add(
				new Point2d(
					cx + y,
					cy));
			leftPts.add(
				new Point2d(
					cx - y,
					cy));
		}
		else if (x == y) {
			upperPts.add(
				new Point2d(
					cx + x,
					cy + y));
			leftPts.add(
				new Point2d(
					cx - x,
					cy + y));
			rightPts.add(
				new Point2d(
					cx + x,
					cy - y));
			lowerPts.add(
				new Point2d(
					cx - x,
					cy - y));
		}
		else if (x < y) {
			upperPts.add(
				new Point2d(
					cx + x,
					cy + y));
			upperPts.add(
				0,
				new Point2d(
					cx - x,
					cy + y));
			lowerPts.add(
				0,
				new Point2d(
					cx + x,
					cy - y));
			lowerPts.add(
				new Point2d(
					cx - x,
					cy - y));
			rightPts.add(
				0,
				new Point2d(
					cx + y,
					cy + x));
			rightPts.add(
				new Point2d(
					cx + y,
					cy - x));
			leftPts.add(
				new Point2d(
					cx - y,
					cy + x));
			leftPts.add(
				0,
				new Point2d(
					cx - y,
					cy - x));
		}
	}

	public static ArrayList<Point2d> getArrowPoints(
		final Point2d anchor,
		final Point2d direction,
		final int width ) {
		final double widthRel = (double) width / 15;
		return getArrowPoints(
			anchor,
			direction,
			widthRel);
	}

	public static ArrayList<Point2d> getArrowPoints(
		final Point2d anchor,
		final Point2d direction,
		final double widthRel ) {
		return getArrowPoints(
			anchor,
			direction,
			widthRel,
			0.75,
			0.5);
	}

	public static ArrayList<Point2d> getArrowPoints(
		final Point2d anchor,
		final Point2d direction,
		final double widthRel,
		final double headRel,
		final double sideRel ) {
		final ArrayList<Point2d> pts = new ArrayList<Point2d>();
		final Vector2d directionVec = new Vector2d();
		directionVec.sub(
			direction,
			anchor);

		directionVec.scale(
			1 - headRel);

		final Point2d midPt = new Point2d();
		midPt.sub(
			direction,
			directionVec);

		directionVec.normalize();
		final Vector2d upVec = new Vector2d(
			directionVec.y,
			-directionVec.x);
		final Vector2d downVec = new Vector2d(
			-directionVec.y,
			directionVec.x);
		final double dist = anchor.distance(
			direction);

		final double pointerDist = dist * widthRel;
		upVec.scale(
			pointerDist * sideRel);
		Point2d temp = new Point2d();
		temp.add(
			anchor,
			upVec);
		pts.add(
			temp);
		temp = new Point2d();
		temp.add(
			midPt,
			upVec);
		pts.add(
			temp);
		temp = new Point2d();
		upVec.scale(
			1 / sideRel);
		temp.add(
			midPt,
			upVec);
		pts.add(
			temp);
		pts.add(
			direction);
		downVec.scale(
			pointerDist);
		temp = new Point2d();
		temp.add(
			midPt,
			downVec);
		pts.add(
			temp);
		temp = new Point2d();
		downVec.scale(
			sideRel);
		temp.add(
			midPt,
			downVec);
		pts.add(
			temp);
		temp = new Point2d();
		temp.add(
			anchor,
			downVec);
		pts.add(
			temp);

		return pts;
	}

	public static List<Point2d> getArrowHead(
		final Point2d point,
		final Vector2d direction,
		final int halfWidth,
		final int height ) {
		final ArrayList<Point2d> pts = new ArrayList<Point2d>();
		final double angle = Math.atan2(
			direction.x,
			direction.y);
		pts.add(
			MathUtils.rotateXYPlane(
				point,
				0,
				height,
				angle));
		pts.add(
			MathUtils.rotateXYPlane(
				point,
				-halfWidth,
				0,
				angle));
		pts.add(
			MathUtils.rotateXYPlane(
				point,
				halfWidth,
				0,
				angle));
		return pts;
	}

	public static List<Point2d> interpolateAllPoints(
		final int maxDistance,
		final List<Point2d> pts,
		final boolean loopPoints ) {
		final List<Point2d> retVal = new ArrayList<Point2d>();
		Point2d prevPt = null;
		for (final Point2d pt : pts) {
			if (prevPt == null) {
				prevPt = pt;
				continue;
			}
			final List<Point2d> interpolatedPts = interpolatePointPair(
				maxDistance,
				prevPt,
				pt);
			if (!retVal.isEmpty()) {
				interpolatedPts.remove(
					0);
			}
			retVal.addAll(
				interpolatedPts);
			prevPt = pt;
		}
		if (loopPoints) {
			final List<Point2d> interpolatedPts = interpolatePointPair(
				maxDistance,
				prevPt,
				pts.get(
					0));
			if (!retVal.isEmpty()) {
				interpolatedPts.remove(
					0);
			}
			retVal.addAll(
				interpolatedPts);
		}
		return retVal;
	}

	public static List<Point2d> interpolatePointPair(
		final int maxDistance,
		final Point2d pt1,
		final Point2d pt2 ) {

		List<Point2d> interpolatedPts = new ArrayList<Point2d>();
		interpolatedPts.add(
			pt1);

		if (pt1.distance(
			pt2) < maxDistance) {
			interpolatedPts.add(
				pt2);
			return interpolatedPts;
		}
		final Point2d midPt = new Point2d(
			(pt1.x + pt2.x) / 2,
			(pt1.y + pt2.y) / 2);
		interpolatedPts.add(
			midPt);
		interpolatedPts.add(
			pt2);
		int prevSize;
		do {
			prevSize = interpolatedPts.size();
			interpolatedPts = interpolateAllPoints(
				maxDistance,
				interpolatedPts,
				false);
		}
		while (prevSize != interpolatedPts.size());
		return interpolatedPts;
	}

	public static ArrayList<Point2d> getArcPoints(
		final Point2d center,
		final double radius,
		final double startRadians,
		final double endRadians,
		double radianInc ) {
		if (center != null) {
			final ArrayList<Point2d> pts = new ArrayList<Point2d>();

			final double range = endRadians - startRadians;
			radianInc = getBestIncrementForRange(
				range,
				(int) Math.round(
					range / radianInc));
			for (double radians = startRadians; radians <= endRadians; radians += radianInc) {
				pts.add(
					rotateXYPlane(
						center,
						0,
						radius,
						radians));
			}
			return pts;
		}
		else {
			return getArcPoints(
				radius,
				startRadians,
				endRadians,
				radianInc);
		}
	}

	public static ArrayList<Point2d> getArcPoints(
		final double radius,
		final double startRadians,
		final double endRadians,
		double radianInc ) {
		final ArrayList<Point2d> pts = new ArrayList<Point2d>();
		final double range = endRadians - startRadians;
		radianInc = getBestIncrementForRange(
			range,
			(int) Math.round(
				range / radianInc));
		for (double radians = startRadians; radians <= endRadians; radians += radianInc) {
			pts.add(
				rotateXYPlane(
					0,
					radius,
					radians));
		}
		return pts;
	}

	public static double[] minAndMax(
		final double[][] values ) {
		double[] minAndMax = null;

		// convert 2-dimensional array into 1-dimensional array
		final double[] array = new double[values.length * values[0].length];
		for (int i = 0; i < values.length; i++) {
			for (int j = 0; j < values[i].length; j++) {
				array[(i * values[i].length) + j] = values[i][j];
			}
		}

		// get min and max values from sorted array
		Arrays.sort(
			array);
		minAndMax = new double[2];
		minAndMax[0] = array[0];
		minAndMax[1] = array[array.length - 1];

		return minAndMax;
	}

	static public List<Point2d> getCalloutPoints(
		final int calloutX,
		final int calloutY,
		final int width,
		final int height,
		final int originX,
		final int originY ) {
		final List<Point2d> calloutShape = new ArrayList<Point2d>();
		final double radInc = Math.PI / 12;
		final double rad90 = Math.PI / 2;
		final int size = 12;

		final int llX = calloutX + size;
		final int llY = calloutY + size;
		final int urX = (calloutX + width) - size;
		final int urY = (calloutY + height) - size;
		final List<Point2d> cornerOffsets = new ArrayList<Point2d>();
		for (double rad = 0; rad <= rad90; rad += radInc) {
			cornerOffsets.add(
				new Point2d(
					Math.cos(
						rad) * size,
					Math.sin(
						rad) * size));
		}
		if (originX < calloutX) {
			if (originY < calloutY) {
				// lower-left
				calloutShape.add(
					new Point2d(
						originX,
						originY));
				calloutShape.add(
					new Point2d(
						llX,
						calloutY));
				calloutShape.add(
					new Point2d(
						urX,
						calloutY));
				for (int i = cornerOffsets.size() - 1; i >= 0; i--) {
					final Point2d offset = cornerOffsets.get(
						i);
					calloutShape.add(
						new Point2d(
							urX + offset.x,
							llY - offset.y));
				}
				calloutShape.add(
					new Point2d(
						calloutX + width,
						llY));
				calloutShape.add(
					new Point2d(
						calloutX + width,
						urY));
				for (final Point2d offset : cornerOffsets) {
					calloutShape.add(
						new Point2d(
							urX + offset.x,
							urY + offset.y));
				}
				calloutShape.add(
					new Point2d(
						urX,
						calloutY + height));
				calloutShape.add(
					new Point2d(
						llX,
						calloutY + height));
				for (int i = cornerOffsets.size() - 1; i >= 0; i--) {
					final Point2d offset = cornerOffsets.get(
						i);
					calloutShape.add(
						new Point2d(
							llX - offset.x,
							urY + offset.y));
				}
				calloutShape.add(
					new Point2d(
						calloutX,
						urY));
				calloutShape.add(
					new Point2d(
						calloutX,
						llY));
			}
			else if (originY > (calloutY + height)) {
				// upper-left
				calloutShape.add(
					new Point2d(
						originX,
						originY));
				calloutShape.add(
					new Point2d(
						calloutX,
						urY));
				calloutShape.add(
					new Point2d(
						calloutX,
						llY));
				for (final Point2d offset : cornerOffsets) {
					calloutShape.add(
						new Point2d(
							llX - offset.x,
							llY - offset.y));
				}
				calloutShape.add(
					new Point2d(
						llX,
						calloutY));
				calloutShape.add(
					new Point2d(
						urX,
						calloutY));
				for (int i = cornerOffsets.size() - 1; i >= 0; i--) {
					final Point2d offset = cornerOffsets.get(
						i);
					calloutShape.add(
						new Point2d(
							urX + offset.x,
							llY - offset.y));
				}
				calloutShape.add(
					new Point2d(
						calloutX + width,
						llY));
				calloutShape.add(
					new Point2d(
						calloutX + width,
						urY));
				for (final Point2d offset : cornerOffsets) {
					calloutShape.add(
						new Point2d(
							urX + offset.x,
							urY + offset.y));
				}
				calloutShape.add(
					new Point2d(
						urX,
						calloutY + height));
				calloutShape.add(
					new Point2d(
						llX,
						calloutY + height));
			}
			else {
				// TODO left
			}
		}
		else if (originX > (calloutX + width)) {
			if (originY < calloutY) {
				// lower-right
				calloutShape.add(
					new Point2d(
						originX,
						originY));
				calloutShape.add(
					new Point2d(
						calloutX + width,
						llY));
				calloutShape.add(
					new Point2d(
						calloutX + width,
						urY));
				for (final Point2d offset : cornerOffsets) {
					calloutShape.add(
						new Point2d(
							urX + offset.x,
							urY + offset.y));
				}
				calloutShape.add(
					new Point2d(
						urX,
						calloutY + height));
				calloutShape.add(
					new Point2d(
						llX,
						calloutY + height));
				for (int i = cornerOffsets.size() - 1; i >= 0; i--) {
					final Point2d offset = cornerOffsets.get(
						i);
					calloutShape.add(
						new Point2d(
							llX - offset.x,
							urY + offset.y));
				}
				calloutShape.add(
					new Point2d(
						calloutX,
						urY));
				calloutShape.add(
					new Point2d(
						calloutX,
						llY));
				for (final Point2d offset : cornerOffsets) {
					calloutShape.add(
						new Point2d(
							llX - offset.x,
							llY - offset.y));
				}
				calloutShape.add(
					new Point2d(
						llX,
						calloutY));
				calloutShape.add(
					new Point2d(
						urX,
						calloutY));
			}
			else if (originY > (calloutY + height)) {
				// upper-right
				calloutShape.add(
					new Point2d(
						originX,
						originY));
				calloutShape.add(
					new Point2d(
						urX,
						calloutY + height));
				calloutShape.add(
					new Point2d(
						llX,
						calloutY + height));
				for (int i = cornerOffsets.size() - 1; i >= 0; i--) {
					final Point2d offset = cornerOffsets.get(
						i);
					calloutShape.add(
						new Point2d(
							llX - offset.x,
							urY + offset.y));
				}
				calloutShape.add(
					new Point2d(
						calloutX,
						urY));
				calloutShape.add(
					new Point2d(
						calloutX,
						llY));
				for (final Point2d offset : cornerOffsets) {
					calloutShape.add(
						new Point2d(
							llX - offset.x,
							llY - offset.y));
				}
				calloutShape.add(
					new Point2d(
						llX,
						calloutY));
				calloutShape.add(
					new Point2d(
						urX,
						calloutY));
				for (int i = cornerOffsets.size() - 1; i >= 0; i--) {
					final Point2d offset = cornerOffsets.get(
						i);
					calloutShape.add(
						new Point2d(
							urX + offset.x,
							llY - offset.y));
				}
				calloutShape.add(
					new Point2d(
						calloutX + width,
						llY));
				calloutShape.add(
					new Point2d(
						calloutX + width,
						urY));
			}
			else {
				// TODO right
			}
		}
		else {
			if (originY < calloutY) {
				// lower
				final int calloutMidX = calloutX + (width / 2);
				calloutShape.add(
					new Point2d(
						originX,
						originY));
				calloutShape.add(
					new Point2d(
						calloutMidX + size,
						calloutY));
				calloutShape.add(
					new Point2d(
						urX,
						calloutY));
				for (int i = cornerOffsets.size() - 1; i >= 0; i--) {
					final Point2d offset = cornerOffsets.get(
						i);
					calloutShape.add(
						new Point2d(
							urX + offset.x,
							llY - offset.y));
				}
				calloutShape.add(
					new Point2d(
						calloutX + width,
						llY));
				calloutShape.add(
					new Point2d(
						calloutX + width,
						urY));
				for (final Point2d offset : cornerOffsets) {
					calloutShape.add(
						new Point2d(
							urX + offset.x,
							urY + offset.y));
				}
				calloutShape.add(
					new Point2d(
						urX,
						calloutY + height));
				calloutShape.add(
					new Point2d(
						llX,
						calloutY + height));
				for (int i = cornerOffsets.size() - 1; i >= 0; i--) {
					final Point2d offset = cornerOffsets.get(
						i);
					calloutShape.add(
						new Point2d(
							llX - offset.x,
							urY + offset.y));
				}
				calloutShape.add(
					new Point2d(
						calloutX,
						urY));
				calloutShape.add(
					new Point2d(
						calloutX,
						llY));
				for (final Point2d offset : cornerOffsets) {
					calloutShape.add(
						new Point2d(
							llX - offset.x,
							llY - offset.y));
				}
				calloutShape.add(
					new Point2d(
						llX,
						calloutY));
				calloutShape.add(
					new Point2d(
						calloutMidX - size,
						calloutY));
			}
			else if (originY > (calloutY + height)) {
				// upper
				final int calloutMidX = calloutX + (width / 2);
				calloutShape.add(
					new Point2d(
						originX,
						originY));
				calloutShape.add(
					new Point2d(
						calloutMidX - size,
						calloutY + height));
				calloutShape.add(
					new Point2d(
						llX,
						calloutY + height));
				for (int i = cornerOffsets.size() - 1; i >= 0; i--) {
					final Point2d offset = cornerOffsets.get(
						i);
					calloutShape.add(
						new Point2d(
							llX - offset.x,
							urY + offset.y));
				}
				calloutShape.add(
					new Point2d(
						calloutX,
						urY));
				calloutShape.add(
					new Point2d(
						calloutX,
						llY));
				for (final Point2d offset : cornerOffsets) {
					calloutShape.add(
						new Point2d(
							llX - offset.x,
							llY - offset.y));
				}
				calloutShape.add(
					new Point2d(
						llX,
						calloutY));
				calloutShape.add(
					new Point2d(
						urX,
						calloutY));
				for (int i = cornerOffsets.size() - 1; i >= 0; i--) {
					final Point2d offset = cornerOffsets.get(
						i);
					calloutShape.add(
						new Point2d(
							urX + offset.x,
							llY - offset.y));
				}
				calloutShape.add(
					new Point2d(
						calloutX + width,
						llY));
				calloutShape.add(
					new Point2d(
						calloutX + width,
						urY));
				for (final Point2d offset : cornerOffsets) {
					calloutShape.add(
						new Point2d(
							urX + offset.x,
							urY + offset.y));
				}
				calloutShape.add(
					new Point2d(
						urX,
						calloutY + height));

				calloutShape.add(
					new Point2d(
						calloutMidX + size,
						calloutY + height));
			}
			else {
				// TODO inside

			}
		}
		return calloutShape;
	}
}
