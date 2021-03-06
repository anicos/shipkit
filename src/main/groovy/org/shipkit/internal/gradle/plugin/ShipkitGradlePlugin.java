package org.shipkit.internal.gradle.plugin;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.shipkit.internal.gradle.TravisPlugin;
import org.shipkit.internal.gradle.release.CiReleasePlugin;
import org.shipkit.internal.gradle.release.GradlePortalReleasePlugin;

/**
 * Automatically ships your Gradle plugins to the Plugin Portal.
 * Intended for Gradle plugin authors who desire to release automatically, continually.
 * Intended for single-project builds that are Gradle-plugin projects.
 *
 * <ul>
 *     <li>{@link TravisPlugin}</li>
 *     <li>{@link PluginDiscoveryPlugin}</li>
 *     <li>{@link CiReleasePlugin}</li>
 *     <li>{@link GradlePortalReleasePlugin}</li>
 * </ul>
 */
public class ShipkitGradlePlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        project.getPlugins().apply(TravisPlugin.class);
        project.getPlugins().apply(PluginDiscoveryPlugin.class);
        project.getPlugins().apply(CiReleasePlugin.class);
        project.getPlugins().apply(GradlePortalReleasePlugin.class);
    }
}
