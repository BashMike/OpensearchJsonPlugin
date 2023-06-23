package org.opensearch.rest.action.resthandlers;

import org.opensearch.client.node.NodeClient;
import org.opensearch.rest.BaseRestHandler;
import org.opensearch.rest.BytesRestResponse;
import org.opensearch.rest.RestChannel;
import org.opensearch.rest.RestRequest;

public abstract class MyRestHandler extends BaseRestHandler {
    @Override
    protected BaseRestHandler.RestChannelConsumer prepareRequest(RestRequest request, NodeClient client) {
        request.params().forEach((k, v) -> request.param(k));

        return channel -> {
            try {
                initRequest(channel, request, client);
            } catch (final Exception e) {
                channel.sendResponse(new BytesRestResponse(channel, e));
            }
        };
    }

    protected abstract void initRequest(RestChannel restChannel, RestRequest request, NodeClient client) throws Exception;
}
