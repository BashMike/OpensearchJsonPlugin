package org.opensearch.rest.action.model;

import org.opensearch.common.xcontent.ToXContent;
import org.opensearch.common.xcontent.XContentBuilder;
import org.opensearch.common.xcontent.XContentParser;
import org.opensearch.common.xcontent.XContentParserUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class NetworkDevice implements ToXContent {
    public static final String UPS_ADV_OUTPUT_LOAD                = "ups_adv_output_load"               ;
    public static final String UPS_ADV_BATTERY_TEMPERATURE        = "ups_adv_battery_temperature"       ;
    public static final String TIMESTAMP                          = "@timestamp"                        ;
    public static final String HOST                               = "host"                              ;
    public static final String UPS_ADV_BATTERY_RUN_TIME_REMAINING = "ups_adv_battery_run_time_remaining";
    public static final String UPS_ADV_OUTPUT_VOLTAGE             = "ups_adv_output_voltage"            ;

    private String        id                            = null;

    private Integer       upsAdvOutputLoad              = null;
    private Integer       upsAdvBatteryTemperature      = null;
    private LocalDateTime timestamp                     = null;
    private String        host                          = null;
    private Integer       upsAdvBatteryRunTimeRemaining = null;
    private Integer       upsAdvOutputVoltage           = null;

    @Override
    public XContentBuilder toXContent(XContentBuilder builder, Params params) throws IOException {
        builder.startObject()
            .field(UPS_ADV_OUTPUT_LOAD               , getUpsAdvOutputLoad())
            .field(UPS_ADV_BATTERY_TEMPERATURE       , getUpsAdvBatteryTemperature())
            .field(TIMESTAMP                         , getTimestamp())
            .field(HOST                              , getHost())
            .field(UPS_ADV_BATTERY_RUN_TIME_REMAINING, getUpsAdvBatteryRunTimeRemaining())
            .field(UPS_ADV_OUTPUT_VOLTAGE            , getUpsAdvOutputVoltage())
            .endObject();

        return builder;
    }

    public static NetworkDevice parse(XContentParser parser, String networkDeviceId) throws IOException {
        NetworkDevice networkDevice = new NetworkDevice();
        networkDevice.setId(networkDeviceId);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSVV");
        XContentParserUtils.ensureExpectedToken(XContentParser.Token.START_OBJECT, parser.currentToken(), parser);
        while (!XContentParser.Token.END_OBJECT.equals(parser.nextToken())) {
            String fieldName = parser.currentName();
            parser.nextToken();
            try {
                switch (fieldName) {
                    case UPS_ADV_OUTPUT_LOAD:                networkDevice.setUpsAdvOutputLoad(parser.intValue()); break;
                    case UPS_ADV_BATTERY_TEMPERATURE:        networkDevice.setUpsAdvBatteryTemperature(parser.intValue()); break;
                    case TIMESTAMP:                          networkDevice.setTimestamp(LocalDateTime.parse(parser.textOrNull(), formatter)); break;
                    case HOST:                               networkDevice.setHost(parser.textOrNull()); break;
                    case UPS_ADV_BATTERY_RUN_TIME_REMAINING: networkDevice.setUpsAdvBatteryRunTimeRemaining(parser.intValue()); break;
                    case UPS_ADV_OUTPUT_VOLTAGE:             networkDevice.setUpsAdvOutputVoltage(parser.intValue()); break;
                }
            }
            catch (Exception e) {} // Ignoring invalid fields
        }

        return networkDevice;
    }


    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Integer getUpsAdvOutputLoad() { return this.upsAdvOutputLoad; }
    public void setUpsAdvOutputLoad(Integer value) { this.upsAdvOutputLoad = value; }

    public Integer getUpsAdvBatteryTemperature() { return this.upsAdvBatteryTemperature; }
    public void setUpsAdvBatteryTemperature(Integer value) { this.upsAdvBatteryTemperature = value; }

    public LocalDateTime getTimestamp() { return this.timestamp; }
    public void setTimestamp(LocalDateTime value) { this.timestamp = value; }

    public String getHost() { return this.host; }
    public void setHost(String value) { this.host = value; }

    public Integer getUpsAdvBatteryRunTimeRemaining() { return this.upsAdvBatteryRunTimeRemaining; }
    public void setUpsAdvBatteryRunTimeRemaining(Integer value) { this.upsAdvBatteryRunTimeRemaining = value; }

    public Integer getUpsAdvOutputVoltage() { return this.upsAdvOutputVoltage; }
    public void setUpsAdvOutputVoltage(Integer value) { this.upsAdvOutputVoltage = value; }
}
