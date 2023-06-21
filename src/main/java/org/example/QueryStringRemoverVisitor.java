package org.example;

import com.github.javaparser.JavaParser;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.ModifierVisitor;

// Visitor class to remove QueryString generation from methods
public class QueryStringRemoverVisitor extends ModifierVisitor<Void> {

    public String parse(){
        String javaCode = "package com.jnj.omega.omp.common.dao.impl.plan;\n" +
                "\n" +
                "import com.jnj.adf.client.api.query.QueryHelper;\n" +
                "import com.jnj.omega.omp.common.IConstant;\n" +
                "import com.jnj.omega.omp.common.dao.impl.CommonDaoImpl;\n" +
                "import com.jnj.omega.omp.common.entity.plan.PlanMddPlanParameterEntity;\n" +
                "import org.apache.commons.lang3.StringUtils;\n" +
                "\n" +
                "import java.util.ArrayList;\n" +
                "import java.util.Collections;\n" +
                "import java.util.List;\n" +
                "import java.util.Map;\n" +
                "\n" +
                "public class PlanMddPlanParameterDaoImplOriginal extends CommonDaoImpl {\n" +
                "\n" +
                "    private static PlanMddPlanParameterDaoImplOriginal instance;\n" +
                "\n" +
                "    private static final String SOURCE_SYSTEM = \"sourceSystem\";\n" +
                "    private static final String DATE_OBJECT = \"dataObject\";\n" +
                "    private static final String ATTRIBUTE = \"attribute\";\n" +
                "    private static final String PARAMETER = \"parameter\";\n" +
                "    private static final String SEGMENT = \"segment\";\n" +
                "    private static final String INCL_EXCL = \"inclExcl\";\n" +
                "\n" +
                "    public static PlanMddPlanParameterDaoImplOriginal getInstance() {\n" +
                "        if (instance == null) {\n" +
                "            instance = new PlanMddPlanParameterDaoImplOriginal();\n" +
                "        }\n" +
                "        return instance;\n" +
                "    }\n" +
                "\n" +
                "    public PlanMddPlanParameterEntity getEntity(String paramSourceSystem, String paramDataObject, String paramAttribute,\n" +
                "                                                String paramParameter) {\n" +
                "\n" +
                "        if (StringUtils.isNotEmpty(paramSourceSystem) && StringUtils.isNotEmpty(paramDataObject)\n" +
                "                && StringUtils.isNotEmpty(paramAttribute) && StringUtils.isNotEmpty(paramParameter)) {\n" +
                "            String localQueryString = QueryHelper.buildCriteria(IConstant.PLAN_MDD_PLAN_PARAMETER.SOURCE_SYSTEM).is\n" +
                "                    (paramSourceSystem)\n" +
                "                    .and(IConstant.PLAN_MDD_PLAN_PARAMETER.DATA_OBJECT).is(paramDataObject)\n" +
                "                    .and(IConstant.PLAN_MDD_PLAN_PARAMETER.ATTRIBUTE).is(paramAttribute)\n" +
                "                    .and(IConstant.PLAN_MDD_PLAN_PARAMETER.PARAMETER).is(paramParameter)\n" +
                "                    .toQueryString();\n" +
                "            return queryForObject(IConstant.REGION.PLAN_MDD_PLAN_PARAMETER, localQueryString, PlanMddPlanParameterEntity\n" +
                "                    .class);\n" +
                "        }\n" +
                "\n" +
                "        return null;\n" +
                "    }\n" +
                "\n" +
                "    public List<Map.Entry<String, String>> queryWithSrcSysCd(String srcSysCd, String selectionScope, String segment) {\n" +
                "        if (StringUtils.isNotBlank(srcSysCd) && StringUtils.isNotBlank(selectionScope) && StringUtils.isNotBlank(segment)) {\n" +
                "            String localQueryString = QueryHelper.buildCriteria(IConstant.PLAN_MDD_SEL_SET_FILTER.LOCAL_SRC_SYS_CD).is(srcSysCd).\n" +
                "                    and(IConstant.PLAN_MDD_SEL_SET_FILTER.SELECTION_SCOPE).is(selectionScope).\n" +
                "                    and(IConstant.PLAN_MDD_SEL_SET_FILTER.SEGMENT).is(segment)\n" +
                "                    .toQueryString();\n" +
                "            return AdfViewHelper.queryForList(IConstant.REGION.MDD_SEL_SET_FILTER, localQueryString, -1);\n" +
                "        }\n" +
                "        return Collections.emptyList();\n" +
                "    }\n" +
                "\n" +
                "    public Map<String, Object> getByKey(String srcSysCd, String matlNum, String plntCd) {\n" +
                "        if (StringUtils.isNotEmpty(srcSysCd) && StringUtils.isNotEmpty(matlNum) && StringUtils.isNotEmpty(plntCd)) {\n" +
                "            String localQueryString = JsonObject.create().append(\"srcSysCd\", srcSysCd).\n" +
                "                    append(\"matlNum\", matlNum)\n" +
                "                    .append(\"plntCd\", plntCd).toJson();\n" +
                "\n" +
                "            return AdfViewHelper.get(IConstant.REGION.MATL_LOC, localQueryString);\n" +
                "        }\n" +
                "        return null;\n" +
                "    }\n" +
                "\n" +
                "\n" +
                "    public PlanMddPlanParameterEntity getEntity(String paramSelectionScope) {\n" +
                "\n" +
                "        if (StringUtils.isNotEmpty(paramSelectionScope)) {\n" +
                "            String localQueryString = QueryHelper.buildCriteria(IConstant.PLAN_MDD_PLAN_PARAMETER.SOURCE_SYSTEM).is\n" +
                "                    (paramSelectionScope).toQueryString();\n" +
                "            return queryForObject(IConstant.REGION.PLAN_MDD_PLAN_PARAMETER, localQueryString, PlanMddPlanParameterEntity\n" +
                "                    .class);\n" +
                "        }\n" +
                "        return null;\n" +
                "    }\n" +
                "\n" +
                "\n" +
                "\n" +
                "    public PlanMddPlanParameterEntity getEntity(String paramSourceSystem, String paramDataObject, String paramAttribute,\n" +
                "                                                String paramParameter,String segment) {\n" +
                "\n" +
                "        if (StringUtils.isNotEmpty(paramSourceSystem) && StringUtils.isNotEmpty(paramDataObject)\n" +
                "                && StringUtils.isNotEmpty(paramAttribute) && StringUtils.isNotEmpty(paramParameter) && StringUtils.isNotEmpty(segment)) {\n" +
                "            String localQueryString = QueryHelper.buildCriteria(IConstant.PLAN_MDD_PLAN_PARAMETER.SOURCE_SYSTEM).is\n" +
                "                    (paramSourceSystem)\n" +
                "                    .and(IConstant.PLAN_MDD_PLAN_PARAMETER.DATA_OBJECT).is(paramDataObject)\n" +
                "                    .and(IConstant.PLAN_MDD_PLAN_PARAMETER.ATTRIBUTE).is(paramAttribute)\n" +
                "                    .and(IConstant.PLAN_MDD_PLAN_PARAMETER.PARAMETER).is(paramParameter)\n" +
                "                    .and(IConstant.PLAN_MDD_PLAN_PARAMETER.SEGMENT).is(segment)\n" +
                "                    .toQueryString();\n" +
                "            return queryForObject(IConstant.REGION.PLAN_MDD_PLAN_PARAMETER, localQueryString, PlanMddPlanParameterEntity\n" +
                "                    .class);\n" +
                "        }\n" +
                "\n" +
                "        return null;\n" +
                "    }\n" +
                "\n" +
                "    public String getParameterValue(String sourceSystem, String dataObject, String attribute, String parameter, String segment) {\n" +
                "        if (StringUtils.isNotBlank(sourceSystem) && StringUtils.isNotBlank(dataObject) && StringUtils.isNotBlank(attribute) && StringUtils.isNotBlank(parameter) && StringUtils.isNotBlank(segment)) {\n" +
                "            String queryString = QueryHelper.buildCriteria(SOURCE_SYSTEM).is(sourceSystem)\n" +
                "                    .and(DATE_OBJECT).is(dataObject)\n" +
                "                    .and(ATTRIBUTE).is(attribute)\n" +
                "                    .and(PARAMETER).is(parameter)\n" +
                "                    .and(SEGMENT).is(segment).toQueryString();\n" +
                "\n" +
                "            PlanMddPlanParameterEntity planMddPlanParameterEntity = queryForObject(IConstant.REGION.PLAN_MDD_PLAN_PARAMETER, queryString, PlanMddPlanParameterEntity\n" +
                "                    .class);\n" +
                "\n" +
                "            if (null != planMddPlanParameterEntity) {\n" +
                "                return planMddPlanParameterEntity.getParameterValue();\n" +
                "            }\n" +
                "        }\n" +
                "        return StringUtils.EMPTY;\n" +
                "\n" +
                "    }\n" +
                "\n" +
                "    public List<String> getParameterValue(String segment,String dataObject){\n" +
                "        ArrayList<String> parameterValueList = new ArrayList<>();\n" +
                "        String queryString = QueryHelper.buildCriteria(SEGMENT).is(segment)\n" +
                "                .and(SOURCE_SYSTEM).is(IConstant.VALUE.OMP)\n" +
                "                .and(DATE_OBJECT).is(dataObject)\n" +
                "                .and(ATTRIBUTE).is(IConstant.VALUE.RESTRICT_SELECT)\n" +
                "                .and(PARAMETER).is(IConstant.VALUE.ORD_LVL_REQ_DLV_DT)\n" +
                "                .and(INCL_EXCL).is(IConstant.VALUE.I).toQueryString();\n" +
                "\n" +
                "        List<PlanMddPlanParameterEntity> planMddPlanParameterEntityList = queryForList(IConstant.REGION.PLAN_MDD_PLAN_PARAMETER, queryString, PlanMddPlanParameterEntity.class);\n" +
                "        if(!planMddPlanParameterEntityList.isEmpty()){\n" +
                "            planMddPlanParameterEntityList.forEach(entity -> {\n" +
                "                        if(StringUtils.isNotEmpty(entity.getParameterValue())){\n" +
                "                            parameterValueList.add(entity.getParameterValue());\n" +
                "                        }\n" +
                "                    }\n" +
                "            );\n" +
                "\n" +
                "        }\n" +
                "        return parameterValueList;\n" +
                "    }\n" +
                "\n" +
                "    public String getValueByDfltdemgrpHistory(String segment) {\n" +
                "        String queryString = QueryHelper.buildCriteria(SOURCE_SYSTEM).is(IConstant.VALUE.OMP)\n" +
                "                .and(DATE_OBJECT).is(IConstant.VALUE.DEMAND_HIST)\n" +
                "                .and(ATTRIBUTE).is(IConstant.VALUE.DEFAULT_ASSIGN)\n" +
                "                .and(PARAMETER).is(IConstant.VALUE.DEMAND_GROUP)\n" +
                "                .and(SEGMENT).is(segment).toQueryString();\n" +
                "\n" +
                "        PlanMddPlanParameterEntity planMddPlanParameterEntity = queryForObject(IConstant.REGION.PLAN_MDD_PLAN_PARAMETER, queryString, PlanMddPlanParameterEntity\n" +
                "                .class);\n" +
                "\n" +
                "        if (null != planMddPlanParameterEntity) {\n" +
                "            return planMddPlanParameterEntity.getParameterValue();\n" +
                "        }\n" +
                "        return StringUtils.EMPTY;\n" +
                "\n" +
                "    }\n" +
                "\n" +
                "    public List<String> getparameterValueList(String segment){\n" +
                "        ArrayList<String> parameterValueList = new ArrayList<>();\n" +
                "        String localQueryString = QueryHelper.buildCriteria(IConstant.PLAN_MDD_PLAN_PARAMETER.SOURCE_SYSTEM).is(IConstant.VALUE.OMP)\n" +
                "                .and(IConstant.PLAN_MDD_PLAN_PARAMETER.DATA_OBJECT).is(IConstant.VALUE.HARD_COMMIT)\n" +
                "                .and(IConstant.PLAN_MDD_PLAN_PARAMETER.ATTRIBUTE).is(IConstant.VALUE.RESTRICT_SELECT)\n" +
                "                .and(IConstant.PLAN_MDD_PLAN_PARAMETER.PARAMETER).is(IConstant.VALUE.SOURCE_SYSTEM)\n" +
                "                .and(IConstant.PLAN_MDD_PLAN_PARAMETER.SEGMENT).is(segment)\n" +
                "                .toQueryString();\n" +
                "\n" +
                "        List<PlanMddPlanParameterEntity> planMddPlanParameterEntityList = queryForList(IConstant.REGION.PLAN_MDD_PLAN_PARAMETER, localQueryString, PlanMddPlanParameterEntity.class);\n" +
                "\n" +
                "        if(!planMddPlanParameterEntityList.isEmpty()){\n" +
                "            planMddPlanParameterEntityList.forEach(entity -> {\n" +
                "                        if(StringUtils.isNotEmpty(entity.getParameterValue())){\n" +
                "                            parameterValueList.add(entity.getParameterValue());\n" +
                "                        }\n" +
                "                    }\n" +
                "            );\n" +
                "\n" +
                "        }\n" +
                "        return parameterValueList;\n" +
                "\n" +
                "    }\n" +
                "\n" +
                "    public String getValueByDfltdemgrp(String segment) {\n" +
                "            String queryString = QueryHelper.buildCriteria(SOURCE_SYSTEM).is(IConstant.VALUE.OMP)\n" +
                "                .and(DATE_OBJECT).is(IConstant.VALUE.DEMAND)\n" +
                "                .and(ATTRIBUTE).is(IConstant.VALUE.DEFAULT_ASSIGN)\n" +
                "                .and(PARAMETER).is(IConstant.VALUE.DEMAND_GROUP)\n" +
                "                .and(SEGMENT).is(segment).toQueryString();\n" +
                "\n" +
                "        PlanMddPlanParameterEntity planMddPlanParameterEntity = queryForObject(IConstant.REGION.PLAN_MDD_PLAN_PARAMETER, queryString, PlanMddPlanParameterEntity\n" +
                "                .class);\n" +
                "\n" +
                "        if (null != planMddPlanParameterEntity) {\n" +
                "            return planMddPlanParameterEntity.getParameterValue();\n" +
                "        }\n" +
                "        return StringUtils.EMPTY;\n" +
                "\n" +
                "    }\n" +
                "\n" +
                "    public List<String> getParameterValues(String segment,String dataObject, String attribute, String parameter){\n" +
                "        ArrayList<String> parameterValueList = new ArrayList<>();\n" +
                "        String queryString = QueryHelper.buildCriteria(SEGMENT).is(segment)\n" +
                "                .and(SOURCE_SYSTEM).is(IConstant.VALUE.OMP)\n" +
                "                .and(DATE_OBJECT).is(dataObject)\n" +
                "                .and(ATTRIBUTE).is(attribute)\n" +
                "                .and(PARAMETER).is(parameter).toQueryString();\n" +
                "\n" +
                "        List<PlanMddPlanParameterEntity> planMddPlanParameterEntityList = queryForList(IConstant.REGION.PLAN_MDD_PLAN_PARAMETER, queryString, PlanMddPlanParameterEntity.class);\n" +
                "        if(!planMddPlanParameterEntityList.isEmpty()){\n" +
                "            planMddPlanParameterEntityList.forEach(entity -> {\n" +
                "                        if(StringUtils.isNotEmpty(entity.getParameterValue())){\n" +
                "                            parameterValueList.add(entity.getParameterValue());\n" +
                "                        }\n" +
                "                    }\n" +
                "            );\n" +
                "\n" +
                "        }\n" +
                "        return parameterValueList;\n" +
                "    }\n" +
                "\n" +
                "    public PlanMddPlanParameterEntity getEntityFetchKey(String srcSysCd, String slsOrdrDocId, String saleDocSchedLineNbr, String coCd, String slsOrdrTypeCd) {\n" +
                "\n" +
                "        if (StringUtils.isNotEmpty(srcSysCd) && StringUtils.isNotEmpty(slsOrdrDocId)\n" +
                "                && StringUtils.isNotEmpty(saleDocSchedLineNbr) && StringUtils.isNotEmpty(coCd)\n" +
                "                && StringUtils.isNotEmpty(slsOrdrTypeCd)) {\n" +
                "\n" +
                "            String localQueryString = JsonObject.create().append(IConstant.SLS_ORDR_SCHED_LINE_DELV.SRCSYSCD, srcSysCd)\n" +
                "                    .append(IConstant.SLS_ORDR_SCHED_LINE_DELV.SLSORDRDOCID, slsOrdrDocId)\n" +
                "                    .append(IConstant.SLS_ORDR_SCHED_LINE_DELV.SALEDOCSCHEDLINENBR, saleDocSchedLineNbr)\n" +
                "                    .append(IConstant.SLS_ORDR_SCHED_LINE_DELV.COCD, coCd)\n" +
                "                    .append(IConstant.SLS_ORDR_SCHED_LINE_DELV.SLSORDRTYPECD, slsOrdrTypeCd)\n" +
                "                    .append(IConstant.SLS_ORDR_SCHED_LINE_DELV.DELVSCHEDCNTNBR, \"0001\")\n" +
                "                    .toJson();\n" +
                "\n" +
                "            return fetchByKey(IConstant.REGION.SLS_ORDR_SCHED_LINE_DELV, localQueryString, EDMSlsOrdrSchedLineDelvEntity.class);\n" +
                "        }\n" +
                "\n" +
                "        return null;\n" +
                "    }\n" +
                "\n" +
                "}\n";

        // Parse the Java code into a CompilationUnit
        CompilationUnit cu = StaticJavaParser.parse(javaCode);


        // Visit the CompilationUnit and remove QueryString generation from methods
        visit(cu, null);

        // Get the modified code after removing QueryString generation
        String modifiedCode = cu.toString();

        return modifiedCode;
    }

    @Override
    public MethodCallExpr visit(MethodCallExpr methodCallExpr, Void arg) {
        // Check if the method call generates the QueryString
        if (methodCallExpr.getNameAsString().contains("buildCriteria")) {
            // Remove the QueryString generation method call
            methodCallExpr.remove();
        }
        return methodCallExpr;
    }

//    @Override
//    public MethodDeclaration visit(MethodDeclaration methodDeclaration, Void arg) {
//        // Get the method body
//        BlockStmt body = methodDeclaration.getBody().orElse(null);
//
//        if (body != null) {
//            // Remove any statements related to QueryString generation
//            for (Statement statement : body.getStatements()) {
//                if (statement.isExpressionStmt()) {
//                    MethodCallExpr methodCallExpr = statement.asExpressionStmt().getExpression().asMethodCallExpr();
//                    if (methodCallExpr.getNameAsString().equals("generateQueryString")) {
//                        statement.remove();
//                    }
//                }
//            }
//        }
//
//        return methodDeclaration;
//    }
}
