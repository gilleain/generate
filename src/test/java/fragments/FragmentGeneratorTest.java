//package fragments;
//
//import org.junit.Test;
//
//import fragments.MetaTree;
//import graph.model.Graph;
//
//public class FragmentGeneratorTest {
//    
//    @Test
//    public void three3_two2_one3_metaTreeA() {
//        MetaTree mtA = new MetaTree(new Graph("0:1,1:2"));
//        
//        Graph fragA0 = new Graph();
//        fragA0.makeVertex();
//        mtA.addPartMapping(0, fragA0);
//        
//        Graph fragA1 = new Graph("0:1,0:2,1:3,2:3");
//        mtA.addPartMapping(1, fragA1);
//        
//        Graph fragA2 = new Graph("0:1,0:2");
//        mtA.addPartMapping(2, fragA2);
//        
//        // attach the lower end of the 0th metatree edge to the 0th vertex 
//        // of the part corresponding to the metatree vertex 
//        mtA.makeLowerAttachment(0, 0);
//        
//        // attach the upper end of the 0th metatree edge to the 0th vertex 
//        // of the part corresponding to the metatree vertex
//        mtA.makeUpperAttachment(0, 0);
//        
//        mtA.makeLowerAttachment(1, 3);
//        mtA.makeUpperAttachment(1, 0);
//        
//        Graph result = mtA.getGraph();
//        System.out.println(result);
//    }
//    
//}
