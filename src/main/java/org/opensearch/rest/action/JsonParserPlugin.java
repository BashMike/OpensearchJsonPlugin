package org.opensearch.rest.action;

import org.opensearch.cluster.metadata.IndexNameExpressionResolver;
import org.opensearch.cluster.node.DiscoveryNodes;
import org.opensearch.common.settings.ClusterSettings;
import org.opensearch.common.settings.IndexScopedSettings;
import org.opensearch.common.settings.Settings;
import org.opensearch.common.settings.SettingsFilter;
import org.opensearch.plugins.ActionPlugin;
import org.opensearch.plugins.Plugin;
import org.opensearch.rest.RestController;
import org.opensearch.rest.RestHandler;
import org.opensearch.rest.action.resthandlers.NetworkDeviceIndexRestHandler;
import org.opensearch.rest.action.resthandlers.NetworkDeviceRootRestHandler;
import org.opensearch.rest.action.resthandlers.NetworkDeviceStatisticsRestHandler;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class JsonParserPlugin extends Plugin implements ActionPlugin {
    @Override
    public List<RestHandler> getRestHandlers(final Settings settings,
                                             final RestController restController,
                                             final ClusterSettings clusterSettings,
                                             final IndexScopedSettings indexScopedSettings,
                                             final SettingsFilter settingsFilter,
                                             final IndexNameExpressionResolver indexNameExpressionResolver,
                                             final Supplier<DiscoveryNodes> nodesInCluster) {

        return Arrays.asList(
                new NetworkDeviceIndexRestHandler(),
                new NetworkDeviceRootRestHandler(),
                new NetworkDeviceStatisticsRestHandler()
        );
    }
}
