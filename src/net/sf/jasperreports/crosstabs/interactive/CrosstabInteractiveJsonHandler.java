/*
 * JasperReports - Free Java Reporting Library.
 * Copyright (C) 2001 - 2013 Jaspersoft Corporation. All rights reserved.
 * http://www.jaspersoft.com
 *
 * Unless you have purchased a commercial license agreement from Jaspersoft,
 * the following license terms apply:
 *
 * This program is part of JasperReports.
 *
 * JasperReports is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JasperReports is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JasperReports. If not, see <http://www.gnu.org/licenses/>.
 */
package net.sf.jasperreports.crosstabs.interactive;

import java.util.LinkedHashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRGenericPrintElement;
import net.sf.jasperreports.engine.JRPropertiesUtil;
import net.sf.jasperreports.engine.ReportContext;
import net.sf.jasperreports.engine.export.GenericElementJsonHandler;
import net.sf.jasperreports.engine.export.JsonExporterContext;
import net.sf.jasperreports.web.util.JacksonUtil;

/**
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: CrosstabInteractiveJsonHandler.java 6412 2013-08-26 16:56:44Z lucianc $
 */
public class CrosstabInteractiveJsonHandler implements GenericElementJsonHandler
{
	
	public static final String PROPERTY_CROSSTAB_ID = JRPropertiesUtil.PROPERTY_PREFIX + "export.crosstab.crosstabId";
	
	public static final String ATTRIBUTE_CROSSTAB_ID = "data-jrxtid";
	
	public static final String PROPERTY_COLUMN_INDEX = JRPropertiesUtil.PROPERTY_PREFIX + "export.crosstab.columnIndex";
	
	public static final String ATTRIBUTE_COLUMN_INDEX = "data-jrxtcolidx";

	public static final String ELEMENT_PARAMETER_CROSSTAB_ID = "crosstabId";

	public static final String ELEMENT_PARAMETER_START_COLUMN_INDEX = "startColumnIndex";

	public static final String ELEMENT_PARAMETER_ROW_GROUPS = "rowGroups";

	public static final String ELEMENT_PARAMETER_DATA_COLUMNS = "dataColumns";
	
	@Override
	public boolean toExport(JRGenericPrintElement element)
	{
		return true;
	}

	@Override
	public String getJsonFragment(JsonExporterContext exporterContext, JRGenericPrintElement element)
	{
		ReportContext reportContext = exporterContext.getExporter().getReportContext();
		String jsonFragment = null;
		if (reportContext != null)
		{
			Map<String, Object> elementInfo = new LinkedHashMap<String, Object>();
			
			String crosstabId = (String) element.getParameterValue(ELEMENT_PARAMETER_CROSSTAB_ID);
			elementInfo.put("type", "crosstab");
			elementInfo.put("module", "jive.crosstab");
			elementInfo.put("uimodule", "jive.crosstab.interactive");
			elementInfo.put("id", crosstabId);
			elementInfo.put("startColumnIndex", element.getParameterValue(ELEMENT_PARAMETER_START_COLUMN_INDEX));			
			elementInfo.put("rowGroups", element.getParameterValue(ELEMENT_PARAMETER_ROW_GROUPS));
			elementInfo.put("dataColumns", element.getParameterValue(ELEMENT_PARAMETER_DATA_COLUMNS));
			
			String elementInfoJson = JacksonUtil.getInstance(exporterContext.getJasperReportsContext()).getJsonString(elementInfo);
			// assuming the Id doesn't need escaping
			jsonFragment = "\"" + crosstabId + "\":" + elementInfoJson;
		}
		return jsonFragment;
	}

}
