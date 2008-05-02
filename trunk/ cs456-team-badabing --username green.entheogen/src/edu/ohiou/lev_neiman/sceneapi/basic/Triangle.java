package edu.ohiou.lev_neiman.sceneapi.basic;

import java.util.*;

import edu.ohiou.lev_neiman.sceneapi.*;

/**
 * <p>Title: class Triangle</p>
 *
 * <p>Description: This class describes a triangle and associated values</p>
 *
 * <p>Copyright: Lev A Neiman 2008</p>
 *
 * <p>Company: Ohio University EECS </p>
 *
 * @author Lev A Neiman
 * @version 1.0
 */
public class Triangle
{
        /**
         * vertex a
         */
        public Coordinate a = null;
        /**
         * vertex b
         */
        public Coordinate b = null;
        /**
         * vertex c
         */
        public Coordinate c = null;
        /**
         * normal for (a-c)x(b-c)
         */
        public Coordinate normal = null;
        /**
         * smooth shaded normal for a
         */
        public Coordinate a_normal = null;
        /**
         * smooth shaded normal for b
         */
        public Coordinate b_normal = null;
        /**
         * smooth shaded nomral for c
         */
        public Coordinate c_normal = null;

        /**
         * Material name that applies to this Triangle
         */
        public String material_name = "Default";
        /**
         * Material reference.
         */
        public Material material;
        Coordinate ta;
        Coordinate tb;
        Coordinate tc;
}
