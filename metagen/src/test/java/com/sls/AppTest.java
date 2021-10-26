package com.sls;

import static org.junit.Assert.assertTrue;

import java.util.Objects;

import com.google.gson.Gson;
import com.sls.generators.AttributeGenerator;
import com.sls.generators.CategoryGenerator;
import com.sls.generators.ComponentGenerator;
import com.sls.generators.GaussianAttrGen;
import com.sls.generators.TraitGenerator;
import com.sls.generators.UniformAttrGen;
import com.sls.properties.Attribute;
import com.sls.properties.Component;
import com.sls.properties.Trait;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    Gson gson = new Gson();

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
    
    @Test
    public void traitGeneratorTest() {
        TraitGenerator tg = new TraitGenerator("EyeType");

        CategoryGenerator round = new CategoryGenerator("ROUND", 20);
        tg.addCategory(round);

        CategoryGenerator square = new CategoryGenerator("SQUARE", 30);
        tg.addCategory(square);

        CategoryGenerator angry = new CategoryGenerator("ANGRY", 20);
        tg.addCategory(angry);
        
        for (int i = 0; i < 10; i++) {
            Trait t = tg.generate();
            assertTrue(
                Objects.equals(t.getCategory(), "ROUND") ||
                Objects.equals(t.getCategory(), "SQUARE") ||
                Objects.equals(t.getCategory(), "ANGRY"));
        }
    }

    @Test
    public void componentListGeneratorTest() {
        ComponentGenerator main = new ComponentGenerator("Main");
        main.addComponentGenerator(new ComponentGenerator("CompGen", 5));;

        Component c = main.generateSingle();
        assertTrue(c.getSubComponentList("CompGen").size() == 5);
    }
}
