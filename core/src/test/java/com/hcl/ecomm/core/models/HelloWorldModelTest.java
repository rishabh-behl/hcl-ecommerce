package com.hcl.ecomm.core.models;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class HelloWorldModelTest {

    private AemContext ctx = new AemContext(ResourceResolverType.JCR_MOCK);
    @BeforeEach
    void setUp() {
        ctx.addModelsForClasses(HelloWorldModel.class);
        //ctx.load().json("com/hcl/ecomm/core/models/HelloWorldModelTest.json","/content");

    }

    @Test
    void getMessage() {
    }

    @Test
    void getText() {

        HelloWorldModel model = ctx.request().adaptTo(HelloWorldModel.class);
        ctx.currentResource("/content/helloworldNew");

        String actualText = "Deepali";
        String expectedText  = model.getText();

        assertEquals(expectedText,actualText);
        System.out.println("This is hello world Component");

    }
}