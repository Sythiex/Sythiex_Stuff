package com.sythiex.sythiexstuff.util;

import net.minecraft.util.math.BlockPos;

public class Math3d
{
	/**
	 * Returns distance between 3D set of coordinates
	 * 
	 * @param x1 - the X coordinate of the first point
	 * @param y1 - the Y coordinate of the first point
	 * @param z1 - the Z coordinate of the first point
	 * @param x2 - the X coordinate of the second point
	 * @param y2 - the Y coordinate of the second point
	 * @param z2 - the Z coordinate of the second point
	 * @return the distance between the points
	 */
	public static double getDistance(float x1, float y1, float z1, float x2, float y2, float z2)
	{
		float dx = x1 - x2;
		float dy = y1 - y2;
		float dz = z1 - z2;
		return Math.sqrt(dx * dx + dy * dy + dz * dz);
	}
	
	/**
	 * Returns the square of the distance from a point to a line. The distance measured is the distance between the specified point and the closest point on the infinitely-extended line defined by the
	 * specified coordinates. If the specified point intersects the line, this method returns 0.0.
	 *
	 * @param x1 - the X coordinate of the start point of the specified line
	 * @param y1 - the Y coordinate of the start point of the specified line
	 * @param z1 - the Z coordinate of the start point of the specified line
	 * @param x2 - the X coordinate of the end point of the specified line
	 * @param y2 - the Y coordinate of the end point of the specified line
	 * @param z2 - the Z coordinate of the end point of the specified line
	 * @param px - the X coordinate of the specified point being measured against the specified line
	 * @param py - the Y coordinate of the specified point being measured against the specified line
	 * @param pz - the Z coordinate of the specified point being measured against the specified line
	 * @return a double value that is the square of the distance from the specified point to the specified line.
	 */
	public static double pointToLineDistSq(double x1, double y1, double z1, double x2, double y2, double z2, double px, double py, double pz)
	{
		// Adjust vectors relative to x1,y1,z1
		// x2,y2 becomes relative vector from x1,y1,z1 to end of segment
		x2 -= x1;
		y2 -= y1;
		z2 -= z1;
		// px,py,pz becomes relative vector from x1,y1,z1 to test point
		px -= x1;
		py -= y1;
		pz -= z1;
		
		double dotprod = px * x2 + py * y2 + pz * z2;
		// dotprod is the length of the px,py vector projected on the x1,y1,z1=>x2,y2,z2
		// vector times the length of the x1,y1,z1=>x2,y2,z2 vector
		double projlenSq = dotprod * dotprod / (x2 * x2 + y2 * y2 + z2 * z2);
		// Distance to line is now the length of the relative point vector minus the
		// length of its projection onto the line
		double lenSq = px * px + py * py + pz * pz - projlenSq;
		
		if(lenSq < 0)
			lenSq = 0;
		
		return lenSq;
	}
	
	/** as {@link #pointToLineDistSq} but takes the square root */
	public static double pointToLineDist(double x1, double y1, double z1, double x2, double y2, double z2, double px, double py, double pz)
	{
		return Math.sqrt(pointToLineDistSq(x1, y1, z1, x2, y2, z2, px, py, pz));
	}
	
	/**
	 * This function tests if the 3D point 'testPos' lies within an arbitrarily oriented cylinder. The cylinder is defined by an axis from 'pos1' to 'pos2', the axis having a length squared of
	 * 'lengthSq', and radius squared of 'radiusSq'.
	 * 
	 * @param pos1
	 * @param pos2
	 * @param lengthSq
	 * @param radiusSq
	 * @param testPos
	 * @return distance squared from cylinder axis if point is inside the cylinder, -1.0 if point is outside the cylinder
	 */
	public static float pointInCylinder(BlockPos pos1, BlockPos pos2, float lengthSq, float radiusSq, BlockPos testPos)
	{
		// vector d from line segment point 1 to point 2
		// translate so point 1 is origin. Make vector from point 1 to point 2.
		float dx = pos2.getX() - pos1.getX();
		float dy = pos2.getY() - pos1.getY();
		float dz = pos2.getZ() - pos1.getZ();
		
		// vector pd from point 1 to test point
		float pdx = testPos.getX() - pos1.getX();
		float pdy = testPos.getY() - pos1.getY();
		float pdz = testPos.getZ() - pos1.getZ();
		
		// Dot the d and pd vectors to see if point lies behind the cylinder cap at point 1
		float dot = pdx * dx + pdy * dy + pdz * dz;
		
		// If dot is less than zero the point is behind the pt1 cap.
		// If greater than the cylinder axis line segment length squared then the point is outside the other end cap at pt2.
		if(dot < 0 || dot > lengthSq)
		{
			return -1;
		}
		else
		{
			/*
			 * Point lies within the parallel caps, so find
			 * distance squared from point to line, using the fact that sin^2 + cos^2 = 1
			 * the dot = cos() * |d||pd|, and cross*cross = sin^2 * |d|^2 * |pd|^2
			 * Carefull: '*' means mult for scalars and dotproduct for vectors
			 * In short, where dist is pt distance to cyl axis:
			 * dist = sin( pd to d ) * |pd|
			 * distsq = dsq = (1 - cos^2( pd to d)) * |pd|^2
			 * dsq = ( 1 - (pd * d)^2 / (|pd|^2 * |d|^2) ) * |pd|^2
			 * dsq = pd * pd - dot * dot / lengthsq
			 * where lengthsq is d*d or |d|^2 that is passed into this function
			 */
			
			// distance squared to the cylinder axis
			float dsq = (pdx * pdx + pdy * pdy + pdz * pdz) - dot * dot / lengthSq;
			
			if(dsq > radiusSq)
			{
				return (-1);
			}
			else
			{
				// return distance squared to axis
				return (dsq);
			}
		}
	}
}
