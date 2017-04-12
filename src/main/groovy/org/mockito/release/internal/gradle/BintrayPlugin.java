package org.mockito.release.internal.gradle;

import com.jfrog.bintray.gradle.BintrayExtension;
import com.jfrog.bintray.gradle.BintrayUploadTask;
import org.gradle.api.*;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;
import org.mockito.release.internal.gradle.util.EnvVariables;
import org.mockito.release.internal.gradle.util.ExtContainer;
import org.mockito.release.internal.gradle.util.LazyConfigurer;

public class BintrayPlugin implements Plugin<Project> {

    /**
     * Name of the task that is configured by this plugin
     */
    static final String BINTRAY_UPLOAD_TASK = "bintrayUpload";

    private final static Logger LOGGER = Logging.getLogger(BintrayPlugin.class);

    public void apply(final Project project) {
        //TODO since this plugin depends on bintray,
        // we need to either shade bintray plugin or ship this Gradle plugin in a separate jar
        // this way we avoid version conflicts and any bintray dependencies for users who don't use bintray
        project.getPlugins().apply("com.jfrog.bintray");

        final BintrayUploadTask bintrayUpload = (BintrayUploadTask) project.getTasks().getByName("bintrayUpload");
        LazyConfigurer.getConfigurer(project).configureLazily(bintrayUpload, new Runnable() {
            @Override
            public void run() {
                bintrayUpload.setApiKey(EnvVariables.getEnv("BINTRAY_API_KEY"));
                if (bintrayUpload.getUser() == null) {
                    //workaround for https://github.com/bintray/gradle-bintray-plugin/issues/170
                    throw new GradleException("Missing Bintray 'user' setting.\n" +
                            "Please configure Bintray extension or the bintrayUpload task so that 'user' is specified.");
                }
            }
        });

        bintrayUpload.doFirst(new Action<Task>() {
            public void execute(Task task) {
                BintrayUploadTask t = (BintrayUploadTask) task;
                LOGGER.lifecycle(t.getPath() + " - publishing to Bintray\n" +
                    "  - dry run: " + t.getDryRun()
                        + ", version: " + t.getVersionName()
                        + ", Maven Central sync: " + t.getSyncToMavenCentral() + "\n" +
                    "  - user/org: " + t.getUser() + "/" + t.getUserOrg()
                        + ", repository/package: " + t.getRepoName() + "/" + t.getPackageName());
            }
        });

        BintrayExtension bintray = project.getExtensions().getByType(BintrayExtension.class);
        ExtContainer ext = new ExtContainer(project.getRootProject());

        bintray.setPublish(true);
        bintray.setDryRun(ext.isReleaseDryRun());

        BintrayExtension.PackageConfig pkg = bintray.getPkg();
        pkg.setDesc(project.getDescription());
        pkg.setPublicDownloadNumbers(true);
        pkg.setWebsiteUrl("https://github.com/" + ext.getGitHubRepository());
        pkg.setIssueTrackerUrl("https://github.com/" + ext.getGitHubRepository() + "/issues");
        pkg.setVcsUrl("https://github.com/" + ext.getGitHubRepository() + ".git");

        pkg.getVersion().setVcsTag("v" + project.getVersion());
        pkg.getVersion().getGpg().setSign(true);
    }
}