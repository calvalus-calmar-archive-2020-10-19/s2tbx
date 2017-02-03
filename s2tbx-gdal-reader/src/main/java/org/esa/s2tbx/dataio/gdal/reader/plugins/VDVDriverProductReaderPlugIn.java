package org.esa.s2tbx.dataio.gdal.reader.plugins;

/**
 * Reader plugin for products using the GDAL library.
 *
 * @author Jean Coravu
 */
public class VDVDriverProductReaderPlugIn extends AbstractDriverProductReaderPlugIn {

    public VDVDriverProductReaderPlugIn() {
        super("VDV", "VDV-451/VDV-452/INTREST Data Format");

        addExtensin(".txt");
        addExtensin(".x10");
    }
}
