package org.opensearch.rest.action.resthandlers;

import org.opensearch.action.ActionListener;
import org.opensearch.action.search.SearchResponse;
import org.opensearch.client.node.NodeClient;
import org.opensearch.rest.*;
import org.opensearch.rest.action.services.NetworkDeviceService;
import org.opensearch.rest.action.utils.Routing;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.opensearch.rest.RestRequest.Method.GET;

public class NetworkDeviceStatisticsRestHandler extends MyRestHandler {
    @Override
    public List<Route> routes() {
        return Collections.unmodifiableList(Arrays.asList(new RestHandler.Route(GET, Routing.STATS_NETWORK_DEVICE_URI)));
    }

    @Override
    public String getName() {
        return "NetworkDevice statistics handler";
    }

    @Override
    protected void initRequest(RestChannel restChannel, RestRequest request, NodeClient client) throws Exception {
        switch (request.method()) {
            case GET: calcStatsOnNetworkDevices(restChannel, request, client); break;
            default: restChannel.sendResponse(new BytesRestResponse(RestStatus.METHOD_NOT_ALLOWED, request.method() + " is not allowed."));
        }
    }

    private void calcStatsOnNetworkDevices(RestChannel restChannel, RestRequest request, NodeClient client) {
        String funcType = request.param("func");
        String fieldName = request.param("field");

        if (funcType == null) {
            throw new IllegalArgumentException("Must specify function type: avg, max, values.");
        }
        else if (fieldName == null) {
            throw new IllegalArgumentException("Must specify field name.");
        }

        NetworkDeviceService.getNetworkDevices(client, new ActionListener<>() {
            @Override
            public void onResponse(SearchResponse searchResponse) {
                if (searchResponse.getHits().getTotalHits().value > 0) {
                    String result = "";
                    try {
                        switch (funcType) {
                            case "avg":    result = NetworkDeviceService.avg(searchResponse, fieldName);    break;
                            case "max":    result = NetworkDeviceService.max(searchResponse, fieldName);    break;
                            case "values": result = NetworkDeviceService.values(searchResponse, fieldName); break;

                            default: throw new IllegalArgumentException("Illegal function type, must be on the following: avg, max, values.");
                        }
                        restChannel.sendResponse(new BytesRestResponse(RestStatus.OK, result));
                    }
                    catch (Exception e) {
                        restChannel.sendResponse(new BytesRestResponse(RestStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
                    }
                } else {
                    restChannel.sendResponse(new BytesRestResponse(RestStatus.OK, "[]"));
                }
            }

            @Override
            public void onFailure(Exception e) {
                restChannel.sendResponse(new BytesRestResponse(RestStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
            }
        });
    }
}
