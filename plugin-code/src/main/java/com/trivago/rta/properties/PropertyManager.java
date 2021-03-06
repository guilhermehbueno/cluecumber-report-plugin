/*
 * Copyright 2018 trivago N.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.trivago.rta.properties;

import com.trivago.rta.exceptions.CluecumberPluginException;
import com.trivago.rta.exceptions.properties.WrongOrMissingPropertyException;
import com.trivago.rta.logging.CluecumberLogger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Map;

@Singleton
public class PropertyManager {

    private final CluecumberLogger logger;

    private String sourceJsonReportDirectory;
    private String generatedHtmlReportDirectory;
    private Map<String, String> customParameters;

    @Inject
    public PropertyManager(final CluecumberLogger logger) {
        this.logger = logger;
    }

    public String getSourceJsonReportDirectory() {
        return sourceJsonReportDirectory;
    }

    public void setSourceJsonReportDirectory(final String reportDirectory) {
        this.sourceJsonReportDirectory = reportDirectory;
    }

    public String getGeneratedHtmlReportDirectory() {
        return generatedHtmlReportDirectory;
    }

    public void setGeneratedHtmlReportDirectory(final String generatedHtmlReportDirectory) {
        this.generatedHtmlReportDirectory = generatedHtmlReportDirectory;
    }

    public Map<String, String> getCustomParameters() {
        return customParameters;
    }

    public void setCustomParameters(final Map<String, String> customParameters) {
        this.customParameters = customParameters;
    }

    /**
     * Checks the pom settings for the plugin.
     *
     * @throws CluecumberPluginException Thrown when a required setting
     *                                   is not specified in the pom.
     */
    public void validateSettings() throws CluecumberPluginException {
        String missingProperty = null;
        if (sourceJsonReportDirectory == null || sourceJsonReportDirectory.equals("")) {
            missingProperty = "sourceJsonReportDirectory";
        } else if (generatedHtmlReportDirectory == null || generatedHtmlReportDirectory.equals("")) {
            missingProperty = "generatedHtmlReportDirectory";
        }

        if (missingProperty != null) {
            throw new WrongOrMissingPropertyException(missingProperty);
        }
    }

    public void logProperties() {
        logger.info("- sourceJsonReportDirectory     : " + sourceJsonReportDirectory);
        logger.info("- generatedHtmlReportDirectory  : " + generatedHtmlReportDirectory);
        if (customParameters != null && !customParameters.isEmpty()) {
            for (Map.Entry<String, String> entry : customParameters.entrySet()) {
                logger.info("- custom parameter              : " +
                        entry.getKey() + " -> " + entry.getValue());
            }
        }
    }
}
