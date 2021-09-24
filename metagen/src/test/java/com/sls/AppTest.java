package com.sls;

import static org.junit.Assert.assertTrue;

import javax.swing.plaf.synth.SynthStyle;

import com.sls.attribute.Attribute;
import com.sls.attribute.AttributeGenerator;
import com.sls.attribute.GaussianAttrGen;
import com.sls.attribute.UniformAttrGen;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }
    
    @Test
    public void gaussianAttrGenTest() {
        AttributeGenerator ag = new GaussianAttrGen("Width", 2, 1);        
        for (int i = 0; i < 10; i++) {
            Attribute a = ag.generate();
            assertTrue(a.getName().equals("Width"));
            assertTrue(a.getRarity() <= 1);
            assertTrue(a.getRarity() >= 0);
        }
    }
    
    @Test
    public void uniformAttrGenTest() {
        UniformAttrGen ag = new UniformAttrGen("Width", 1, 3);        
        for (int i = 0; i < 10; i++) {
            Attribute a = ag.generate();
            assertTrue(a.getName().equals("Width"));
            assertTrue(a.getRarity() <= 1);
            assertTrue(a.getRarity() >= 0);
            assertTrue(a.getValue() >= 1);
            assertTrue(a.getValue() <= 3);
        }
    }
    
}
