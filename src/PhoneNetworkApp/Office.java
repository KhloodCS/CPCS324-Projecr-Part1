/* CPCS324 Project Part 1

 Group members: 
    1- Razan Alamri
    2- Khloud Alsofyani
    3- Leen Ba Galaql
    4- Shatha Binmahfouz
 */
package PhoneNetworkApp;

import GraphFramework.Vertex;

// Office is a subclass of Vertex, it inherits all attributes, operations & relationships
public class Office extends Vertex {

   // Method to set lable
   public void setLabel(String label) {
      super.label = label;
   }

   /*
    * Override method that responsible for displaying
    * the information of the class attributes
    */
   @Override
   public void displyInfo() {
      System.out.print("Office No." + getLabel());
   }
}
