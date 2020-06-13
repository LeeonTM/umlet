package com.baselet.element.facet.customdrawings;

import com.baselet.diagram.draw.helper.StyleException;
import org.junit.Before;
import org.junit.Test;

public class InvalidStyleOptionsTest {

    private DummyDrawHandler drawHandler;

    @Before
    public void before() {
        drawHandler = new DummyDrawHandler();
    }

    @Test(expected = StyleException.class)
    public void drawRectangleInvalidColorParameter() {
        new CustomDrawingParserImpl("drawRectangle(0,0,width,height) lt=- lw=25 bg=drkfghkdelrt fg=dlkfrghtkuderht", 30, 40, drawHandler).parse();
    }

    @Test(expected = CustomDrawingParserRuntimeException.class)
    public void drawRectangleInvalidLineType() {
        new CustomDrawingParserImpl("drawRectangle(0,0,width,height) lt=$ lw=25 bg=red fg=pink", 30, 40, drawHandler).parse();
    }

    @Test(expected = CustomDrawingParserRuntimeException.class)
    public void drawRectangleInvalidLineWidth() {
        new CustomDrawingParserImpl("drawRectangle(0,0,width,height) lt=- lw=-10 bg=red fg=pink", 30, 40, drawHandler).parse();
    }
}
