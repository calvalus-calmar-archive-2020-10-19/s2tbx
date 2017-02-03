package org.esa.s2tbx.dataio.gdal.writer.plugins;

import org.esa.s2tbx.dataio.gdal.GdalInstallInfo;
import org.esa.s2tbx.dataio.gdal.writer.GDALProductWriter;
import org.esa.s2tbx.dataio.gdal.activator.GDALDriverInfo;
import org.esa.snap.core.dataio.EncodeQualification;
import org.esa.snap.core.dataio.ProductWriter;
import org.esa.snap.core.dataio.ProductWriterPlugIn;
import org.esa.snap.core.datamodel.Product;
import org.esa.snap.core.util.io.SnapFileFilter;

import java.io.File;
import java.util.Locale;

/**
 * Writer plugin for products using the GDAL library.
 *
 * @author Jean Coravu
 */
public abstract class AbstractDriverProductWriterPlugIn implements ProductWriterPlugIn {
    private final GDALDriverInfo writerDriver;

    protected AbstractDriverProductWriterPlugIn(String fileExtension, String driverName, String driverDisplayName, String creationDataTypes) {
        this.writerDriver = new GDALDriverInfo(fileExtension, driverName, driverDisplayName, creationDataTypes);
    }

    @Override
    public final String getDescription(Locale locale) {
        return this.writerDriver.getDriverDisplayName();
    }

    @Override
    public final String[] getFormatNames() {
        return new String[] {this.writerDriver.getWriterPluginFormatName()};
    }

    @Override
    public final String[] getDefaultFileExtensions() {
        return new String[] {this.writerDriver.getExtensionName()};
    }

    @Override
    public final SnapFileFilter getProductFileFilter() {
        return null;
    }

    @Override
    public final EncodeQualification getEncodeQualification(Product product) {
        if (GdalInstallInfo.INSTANCE.isPresent()) {
            return new EncodeQualification(EncodeQualification.Preservation.FULL);
        }
        return new EncodeQualification(EncodeQualification.Preservation.UNABLE);
    }

    @Override
    public final Class[] getOutputTypes() {
        return new Class[] {String.class, File.class};
    }

    @Override
    public final ProductWriter createWriterInstance() {
        return new GDALProductWriter(this, this.writerDriver);
    }

    public final GDALDriverInfo getWriterDriver() {
        return this.writerDriver;
    }
}

