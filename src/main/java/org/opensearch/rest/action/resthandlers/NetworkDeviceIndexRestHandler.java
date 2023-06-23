package org.opensearch.rest.action.resthandlers;

import org.opensearch.action.ActionListener;
import org.opensearch.action.delete.DeleteResponse;
import org.opensearch.action.index.IndexResponse;
import org.opensearch.action.search.SearchResponse;
import org.opensearch.action.support.WriteRequest;
import org.opensearch.client.node.NodeClient;
import org.opensearch.common.xcontent.XContentParser;
import org.opensearch.common.xcontent.XContentParserUtils;
import org.opensearch.common.xcontent.json.JsonXContent;
import org.opensearch.rest.*;
import org.opensearch.rest.action.model.NetworkDevice;
import org.opensearch.rest.action.services.NetworkDeviceService;
import org.opensearch.rest.action.utils.Routing;
import org.opensearch.search.SearchHit;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.opensearch.rest.RestRequest.Method.*;
import static org.opensearch.threadpool.ThreadPool.Names.REFRESH;

public class NetworkDeviceIndexRestHandler extends MyRestHandler {
    @Override
    public List<Route> routes() {
        return Collections.unmodifiableList(Arrays.asList(
                new RestHandler.Route(DELETE, Routing.ID_NETWORK_DEVICE_URI),
                new RestHandler.Route(GET   , Routing.ID_NETWORK_DEVICE_URI),
                new RestHandler.Route(PUT   , Routing.ID_NETWORK_DEVICE_URI)));
    }

    @Override
    public String getName() { return "Network device index handler"; }

    @Override
    protected void initRequest(RestChannel restChannel, RestRequest request, NodeClient client) throws Exception {
        switch (request.method()) {
            case GET:    getNetworkDevice(restChannel, request, client); break;
            case PUT:    putNetworkDevice(restChannel, request, client); break;
            case DELETE: deleteNetworkDevice(restChannel, request, client); break;

            default: restChannel.sendResponse(new BytesRestResponse(RestStatus.METHOD_NOT_ALLOWED, request.method() + " is not allowed."));
        }
    }

    private void getNetworkDevice(RestChannel restChannel, RestRequest request, NodeClient client) {
        String id = request.param("id");

        if (id == null) {
            throw new IllegalArgumentException("Must specify id.");
        }

        NetworkDeviceService.getNetworkDevice(id, client, new ActionListener<>() {
            @Override
            public void onResponse(SearchResponse searchResponse) {
                SearchHit hit = searchResponse.getHits().getAt(0);
                String networkDevice = hit.getSourceAsString();

                restChannel.sendResponse(new BytesRestResponse(RestStatus.OK, networkDevice));
            }

            @Override
            public void onFailure(Exception e) {
                restChannel.sendResponse(
                        new BytesRestResponse(RestStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
            }
        });
    }

    private void putNetworkDevice(RestChannel restChannel, RestRequest request, NodeClient client) throws IOException {

        String id = request.param("id");
        XContentParser xcp = request.contentParser();
        XContentParserUtils.ensureExpectedToken(XContentParser.Token.START_OBJECT, xcp.nextToken(), xcp);
        NetworkDevice networkDevice = NetworkDevice.parse(xcp, id);

        WriteRequest.RefreshPolicy refreshPolicy = WriteRequest.RefreshPolicy.parse(request.param(REFRESH,
                WriteRequest.RefreshPolicy.IMMEDIATE.getValue()));

        NetworkDeviceService.putNetworkDevice(id, networkDevice, refreshPolicy, client, new ActionListener<>() {
            @Override
            public void onResponse(IndexResponse indexResponse) {
                try {
                    RestResponse restResponse = new BytesRestResponse(RestStatus.OK,
                            indexResponse.toXContent(JsonXContent.contentBuilder(), null));
                    restChannel.sendResponse(restResponse);
                } catch (IOException e) {
                    restChannel.sendResponse(
                            new BytesRestResponse(RestStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
                }
            }

            @Override
            public void onFailure(Exception e) {
                restChannel.sendResponse(
                        new BytesRestResponse(RestStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
            }
        });
    }

    private void deleteNetworkDevice(RestChannel restChannel, RestRequest request, NodeClient client) {
        String id = request.param("id");

        if (id == null) {
            throw new IllegalArgumentException("Must specify id.");
        }

        WriteRequest.RefreshPolicy refreshPolicy = WriteRequest.RefreshPolicy.parse(request.param(REFRESH,
                WriteRequest.RefreshPolicy.IMMEDIATE.getValue()));

        NetworkDeviceService.deleteNetworkDevice(id, refreshPolicy, client, new ActionListener<>() {
            @Override
            public void onResponse(DeleteResponse deleteResponse) {
                restChannel.sendResponse(
                        new BytesRestResponse(RestStatus.OK, "NetworkDevice deleted."));
            }

            @Override
            public void onFailure(Exception e) {
                restChannel.sendResponse(
                        new BytesRestResponse(RestStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
            }
        });
    }
}
