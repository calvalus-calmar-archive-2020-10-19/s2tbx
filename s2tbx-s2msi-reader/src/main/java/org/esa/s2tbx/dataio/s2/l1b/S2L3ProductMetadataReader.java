package org.esa.s2tbx.dataio.s2.l1b;

import org.esa.s2tbx.dataio.s2.S2Config;
import org.esa.s2tbx.dataio.s2.S2SpatialResolution;
import org.esa.s2tbx.dataio.s2.VirtualPath;
import org.esa.s2tbx.dataio.s2.l3.L3Metadata;
import org.esa.s2tbx.dataio.s2.ortho.AbstractS2OrthoMetadataReader;
import org.esa.s2tbx.dataio.s2.ortho.S2OrthoMetadata;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jcoravu on 10/1/2020.
 */
public class S2L3ProductMetadataReader extends AbstractS2OrthoMetadataReader {

    private final  S2SpatialResolution spatialResolution;

    public S2L3ProductMetadataReader(VirtualPath virtualPath, String epsgCode, S2SpatialResolution spatialResolution) throws IOException {
        super(virtualPath, epsgCode);
        this.spatialResolution = spatialResolution;
    }

    @Override
    protected String[] getBandNames(S2SpatialResolution resolution) {
        return null;
    }

    @Override
    protected List<VirtualPath> getImageDirectories(VirtualPath pathToImages, S2SpatialResolution spatialResolution) throws IOException {
        ArrayList<VirtualPath> imageDirectories = new ArrayList<>();
        String resolutionFolder = "R" + Integer.toString(spatialResolution.resolution) + "m";
        VirtualPath pathToImagesOfResolution = pathToImages.resolve(resolutionFolder);
        VirtualPath[] imagePaths = pathToImagesOfResolution.listPaths();
        if(imagePaths == null || imagePaths.length == 0) {
            return imageDirectories;
        }

        for (VirtualPath imagePath : imagePaths) {
            if (imagePath.getFileName().toString().endsWith("_" + spatialResolution.resolution + "m.jp2")) {
                imageDirectories.add(imagePath);
            }
        }

        return imageDirectories;
    }

    @Override
    protected S2OrthoMetadata parseHeader(
            VirtualPath path, String granuleName, S2Config config, String epsg, boolean isAGranule) throws IOException {

        try {
            return L3Metadata.parseHeader(path, granuleName, config, epsg, spatialResolution, isAGranule, namingConvention);
        } catch (ParserConfigurationException | SAXException e) {
            throw new IOException("Failed to parse metadata in " + path.getFileName().toString());
        }
    }
}
