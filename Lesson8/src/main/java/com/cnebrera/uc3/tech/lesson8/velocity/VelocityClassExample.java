/*********************************************************************
 This class has been automatically generated using Velocity!
 Mon Dec 14 23:17:28 CET 2018
 **********************************************************************/

package com.cnebrera.uc3.tech.lesson8.velocity;

import java.util.List;
import java.util.ArrayList;

public class VelocityClassExample {
    /**
     * Attribute - myListStringValues
     */
    private List<String> myListStringValues;

    /**
     * Public Constructor
     */
    public VelocityClassExample() {
        this.myListStringValues = new ArrayList<>();

        // Set values from Velocity
        this.myListStringValues.add("Hello");
        this.myListStringValues.add("World!");
        System.out.println("Velocity injected values!");
    }

    /**
     * @return myListStringValues as string
     */
    public String toString() {
        return myListStringValues.toString();
    }

    /**
     * @param args with the input arguments
     */
    public static void main(final String[] args) {
        final VelocityClassExample myExample = new VelocityClassExample();
        System.out.println(myExample.toString());
    }
}
