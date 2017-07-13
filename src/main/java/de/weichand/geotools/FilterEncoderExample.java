package de.weichand.geotools;

import java.io.IOException;
import javax.xml.transform.TransformerException;
import org.geotools.filter.text.cql2.CQLException;
import org.geotools.filter.text.ecql.ECQL;
import org.geotools.xml.Configuration;
import org.geotools.xml.Encoder;
import org.opengis.filter.Filter;

/**
 * Geotools 16.1 FilterEncoderExample (CQL Filter)
 *
 * @author Juergen Weichand
 */
public class FilterEncoderExample {

    public static void main(String[] args) throws CQLException, TransformerException, IOException {

        String[] cqls = {
            "\"hk:ags\" = '09162000'",
            "\"hk:ags\" = '09162000' OR \"hk:ags\" = '08128007'",
            "\"hk:ags\" IN ('09162000','08128007')",
            "\"hk:ags\" LIKE '09*'"
        };
        
        
        for (String cql : cqls) 
        {
            System.out.println("--> " + cql);
            Filter filter = ECQL.toFilter(cql);
            Configuration configuration = new org.geotools.filter.v2_0.FESConfiguration();
            Encoder encoder = new Encoder(configuration);
            encoder.setIndenting(true);
            encoder.setIndentSize(3);
            encoder.setOmitXMLDeclaration(true);
            encoder.encode(filter, org.geotools.filter.v2_0.FES.Filter, System.out);   
            System.out.println("\n\n");
        }
        
    }

}
