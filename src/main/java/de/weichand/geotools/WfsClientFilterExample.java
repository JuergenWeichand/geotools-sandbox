package de.weichand.geotools;

import com.vividsolutions.jts.geom.Geometry;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.DefaultQuery;
import org.geotools.data.FeatureSource;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.geotools.filter.text.cql2.CQL;
import org.geotools.filter.text.cql2.CQLException;
import org.opengis.feature.Feature;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.filter.Filter;

/**
 * Geotools 14.1 WfsClient example (CQL Filter)
 *
 * @author Juergen Weichand
 */
public class WfsClientFilterExample 
{

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     * @throws org.geotools.filter.text.cql2.CQLException
     */
    public static void main(String[] args) throws IOException, CQLException 
    {

        // System.setProperty("http.proxyHost", ProxyHost);
        // System.setProperty("http.proxyPort", ProxyPort);
        
        String getCapabilitiesUrl = "http://geoserv.weichand.de:8080/geoserver/wfs?acceptversions=2.0.0&REQUEST=GetCapabilities";

        // create WFS configuration
        Map connectionParameters = new HashMap();
        connectionParameters.put("WFSDataStoreFactory:GET_CAPABILITIES_URL", getCapabilitiesUrl);

        // create DataStore
        DataStore dataStore = DataStoreFinder.getDataStore(connectionParameters);

        // create FeatureSource for "bvv:gmd_ex" ("Gemeinden Bayern")
        String typeName = "bvv_gmd_ex";
        FeatureSource<SimpleFeatureType, SimpleFeature> source = dataStore.getFeatureSource(typeName);

        // create Filter (Amtlicher Gemeindeschlüssel = 09162000)
        Filter filter = CQL.toFilter("sch = '09162000'");

        // requesting Features
        FeatureCollection<SimpleFeatureType, SimpleFeature> features = source.getFeatures(filter);

        // iterate over results
        try (FeatureIterator<SimpleFeature> iterator = features.features()) 
        {
            Feature feature = (Feature) iterator.next();
            if (feature instanceof SimpleFeature) 
            {
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                SimpleFeature simpleFeature = (SimpleFeature) feature;
                System.out.println("--> ID :" + simpleFeature.getID());

                // properties
                System.out.println("--> Gemeinde: " + simpleFeature.getAttribute("bez_gem"));
                System.out.println("--> Landkreis: " + simpleFeature.getAttribute("bez_krs"));

                // JTS-geometry
                Geometry geometry = (Geometry) simpleFeature.getDefaultGeometry();
                System.out.println("--> " + geometry.toString());
            }
        }
    }
}
