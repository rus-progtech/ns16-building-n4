package com.niagarasummit.barcode.ui;

import org.krysalis.barcode4j.BarcodeDimension;
import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;

import javax.baja.gx.BImage;
import javax.baja.gx.Graphics;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.ui.BWidget;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

@NiagaraType
@NiagaraProperty(
        name = "value",
        type = "double",
        defaultValue = "0"
)
@NiagaraProperty(
        name = "text",
        type = "String",
        defaultValue = ""
)

public class BBarcodeGenerator
    extends BWidget {
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.niagarasummit.barcode.ui.BBarcodeGenerator(3796587420)1.0$ @*/
/* Generated Wed Jul 10 15:33:36 ICT 2024 by Slot-o-Matic (c) Tridium, Inc. 2012-2024 */

  //region Property "value"

  /**
   * Slot for the {@code value} property.
   * @see #getValue
   * @see #setValue
   */
  public static final Property value = newProperty(0, 0, null);

  /**
   * Get the {@code value} property.
   * @see #value
   */
  public double getValue() { return getDouble(value); }

  /**
   * Set the {@code value} property.
   * @see #value
   */
  public void setValue(double v) { setDouble(value, v, null); }

  //endregion Property "value"

  //region Property "text"

  /**
   * Slot for the {@code text} property.
   * @see #getText
   * @see #setText
   */
  public static final Property text = newProperty(0, "", null);

  /**
   * Get the {@code text} property.
   * @see #text
   */
  public String getText() { return getString(text); }

  /**
   * Set the {@code text} property.
   * @see #text
   */
  public void setText(String v) { setString(text, v, null); }

  //endregion Property "text"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBarcodeGenerator.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

    public BBarcodeGenerator()  {  }

    @Override
    public void paint(Graphics g)
    {
        try
        {
            //Create the barcode bean
            Code39Bean bean = new Code39Bean();

            //Configure the barcode generator
            final int dpi = 150;
            bean.setModuleWidth(UnitConv.in2mm(1.0f / dpi)); //makes the narrow bar width exactly one pixel
            bean.setWideFactor(3);
            bean.doQuietZone(false);

            //Initialize the text for the barcode
            String barcodeText = df3.format(getValue());

            //Set up the barcode canvas provider for monochrome JPEG output
            ByteArrayOutputStream out = new ByteArrayOutputStream(8*1024);
            BitmapCanvasProvider canvas = new BitmapCanvasProvider(
                    out, "image/jpeg", dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);

            //Generate the barcode
            bean.generateBarcode(canvas, barcodeText);

            //Signal end of generation
            canvas.finish();

            // Paint the image
            BarcodeDimension size = bean.calcDimensions(barcodeText);
            double w = size.getWidth();
            double h = size.getHeight();
            g.drawImage(BImage.make(out.toByteArray()), w, h);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static DecimalFormat df3 = new DecimalFormat("000.000");
}
