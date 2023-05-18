package com.example.demo;

import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hut
 * @date 2023/3/14 2:12 下午
 */
public class KmbmTest4 {
    public static void main(String[] args) {
        String str ="科目余额表\n" +
                "编制单位：新乡市中凯科电电力技术有限公司\n" +
                "科目编码\n" +
                "\n" +
                "1001\n" +
                "1002\n" +
                "1002.01\n" +
                "1002.03\n" +
                "1012\n" +
                "1101\n" +
                "1121\n" +
                "1121.01\n" +
                "1121.02\n" +
                "1121.03\n" +
                "1121.04\n" +
                "1121.05\n" +
                "1121.06\n" +
                "1121.07\n" +
                "1121.08\n" +
                "1121.09\n" +
                "1121.10\n" +
                "1121.11\n" +
                "1121.12\n" +
                "1121.13\n" +
                "1121.14\n" +
                "1121.15\n" +
                "1121.16\n" +
                "1121.17\n" +
                "1121.18\n" +
                "1121.19\n" +
                "1121.20\n" +
                "1121.21\n" +
                "1121.22\n" +
                "1121.23\n" +
                "1121.24\n" +
                "1121.25\n" +
                "1121.26\n" +
                "1121.27\n" +
                "1121.28\n" +
                "1121.29\n" +
                "1121.30\n" +
                "1121.31\n" +
                "1121.32\n" +
                "1121.33\n" +
                "1121.34\n" +
                "1121.35\n" +
                "1121.36\n" +
                "1121.37\n" +
                "1121.38\n" +
                "1121.39\n" +
                "1121.40\n" +
                "1121.41\n" +
                "1121.42\n" +
                "1121.43\n" +
                "1121.44\n" +
                "1121.45\n" +
                "1121.46\n" +
                "1121.47\n" +
                "1121.48\n" +
                "1121.49\n" +
                "1121.50\n" +
                "1121.51\n" +
                "1121.52\n" +
                "1121.53\n" +
                "1121.54\n" +
                "1121.55\n" +
                "1121.56\n" +
                "1121.57\n" +
                "1121.58\n" +
                "1121.59\n" +
                "1121.60\n" +
                "1121.61\n" +
                "1121.62\n" +
                "1121.63\n" +
                "1121.64\n" +
                "1121.65\n" +
                "1121.66\n" +
                "1121.67\n" +
                "1121.68\n" +
                "1121.69\n" +
                "1121.70\n" +
                "1121.71\n" +
                "1121.72\n" +
                "1121.73\n" +
                "1121.74\n" +
                "1121.75\n" +
                "1121.76\n" +
                "1121.77\n" +
                "1121.78\n" +
                "1121.79\n" +
                "1121.80\n" +
                "1121.81\n" +
                "1121.82\n" +
                "1121.83\n" +
                "1121.84\n" +
                "1121.85\n" +
                "1121.86\n" +
                "1121.87\n" +
                "1121.88\n" +
                "1121.89\n" +
                "1121.90\n" +
                "1121.91\n" +
                "1121.92\n" +
                "1121.93\n" +
                "1121.94\n" +
                "1121.95\n" +
                "1121.96\n" +
                "1121.97\n" +
                "1121.98\n" +
                "1121.99\n" +
                "1121.100\n" +
                "1121.101\n" +
                "1121.102\n" +
                "1121.103\n" +
                "1121.104\n" +
                "1121.195\n" +
                "1121.196\n" +
                "1121.197\n" +
                "1121.198\n" +
                "1121.199\n" +
                "1121.200\n" +
                "1121.201\n" +
                "1121.202\n" +
                "1121.203\n" +
                "1122\n" +
                "1122.01\n" +
                "1122.02\n" +
                "1122.03\n" +
                "1122.04\n" +
                "1122.05\n" +
                "1122.06\n" +
                "1122.07\n" +
                "1122.08\n" +
                "1122.09\n" +
                "1122.10\n" +
                "1122.11\n" +
                "1122.12\n" +
                "1122.13\n" +
                "1122.14\n" +
                "1122.15\n" +
                "1122.16\n" +
                "1122.17\n" +
                "1122.18\n" +
                "1122.19\n" +
                "1122.20\n" +
                "1122.21\n" +
                "1122.22\n" +
                "1122.23\n" +
                "1122.24\n" +
                "1122.25\n" +
                "1122.26\n" +
                "1122.27\n" +
                "1122.28\n" +
                "1122.29\n" +
                "1122.30\n" +
                "1122.31\n" +
                "1122.32\n" +
                "1122.33\n" +
                "1122.34\n" +
                "1122.35\n" +
                "1122.36\n" +
                "1122.37\n" +
                "1122.38\n" +
                "1122.39\n" +
                "1122.40\n" +
                "1122.41\n" +
                "1122.42\n" +
                "1122.43\n" +
                "1122.44\n" +
                "1122.45\n" +
                "1122.46\n" +
                "1122.47\n" +
                "1122.48\n" +
                "1122.49\n" +
                "1122.50\n" +
                "1122.51\n" +
                "1122.52\n" +
                "1122.53\n" +
                "1122.54\n" +
                "1122.55\n" +
                "1122.56\n" +
                "1122.57\n" +
                "1122.58\n" +
                "1122.59\n" +
                "1122.60\n" +
                "1122.61\n" +
                "1122.62\n" +
                "1122.63\n" +
                "1122.64\n" +
                "1122.65\n" +
                "1122.66\n" +
                "1122.67\n" +
                "1122.68\n" +
                "1122.69\n" +
                "1122.70\n" +
                "1122.71\n" +
                "1122.72\n" +
                "1122.73\n" +
                "1122.74\n" +
                "1122.75\n" +
                "1122.76\n" +
                "1122.77\n" +
                "1122.78\n" +
                "1122.79\n" +
                "1122.80\n" +
                "1122.81\n" +
                "1122.82\n" +
                "1122.83\n" +
                "1122.84\n" +
                "1122.85\n" +
                "1122.86\n" +
                "1122.87\n" +
                "1122.88\n" +
                "1122.89\n" +
                "1122.90\n" +
                "1122.91\n" +
                "1122.92\n" +
                "1122.93\n" +
                "1122.94\n" +
                "1122.95\n" +
                "1122.96\n" +
                "1122.97\n" +
                "1122.98\n" +
                "1122.99\n" +
                "1122.100\n" +
                "1122.101\n" +
                "1122.102\n" +
                "1122.103\n" +
                "1122.104\n" +
                "1122.105\n" +
                "1122.106\n" +
                "1122.107\n" +
                "1122.108\n" +
                "1122.109\n" +
                "1122.110\n" +
                "1122.111\n" +
                "1122.112\n" +
                "1122.113\n" +
                "1122.114\n" +
                "1122.115\n" +
                "1122.116\n" +
                "1122.117\n" +
                "1122.118\n" +
                "1122.119\n" +
                "1122.120\n" +
                "1122.121\n" +
                "1122.122\n" +
                "1122.123\n" +
                "1122.124\n" +
                "1122.125\n" +
                "1122.126\n" +
                "1122.127\n" +
                "1122.128\n" +
                "1122.129\n" +
                "1122.130\n" +
                "1122.131\n" +
                "1122.132\n" +
                "1122.133\n" +
                "1122.134\n" +
                "1122.135\n" +
                "1122.136\n" +
                "1122.137\n" +
                "1122.138\n" +
                "1122.139\n" +
                "1122.140\n" +
                "1122.141\n" +
                "1122.142\n" +
                "1122.143\n" +
                "1122.144\n" +
                "1122.145\n" +
                "1122.146\n" +
                "1122.147\n" +
                "1122.148\n" +
                "1122.149\n" +
                "1122.150\n" +
                "1122.151\n" +
                "1122.152\n" +
                "1122.153\n" +
                "1122.154\n" +
                "1122.155\n" +
                "1122.156\n" +
                "1122.157\n" +
                "1122.158\n" +
                "1122.159\n" +
                "1122.160\n" +
                "1122.280\n" +
                "1122.281\n" +
                "1122.282\n" +
                "1122.283\n" +
                "1122.284\n" +
                "1122.285\n" +
                "1122.286\n" +
                "1122.287\n" +
                "1122.288\n" +
                "1122.289\n" +
                "1122.290\n" +
                "1122.291\n" +
                "1122.292\n" +
                "1122.293\n" +
                "1122.294\n" +
                "1122.295\n" +
                "1122.296\n" +
                "1122.297\n" +
                "1122.298\n" +
                "1122.299\n" +
                "1122.300\n" +
                "1122.301\n" +
                "1123\n" +
                "1131\n" +
                "1132\n" +
                "1221\n" +
                "1221.01\n" +
                "1221.02\n" +
                "1221.03\n" +
                "1221.04\n" +
                "1221.11\n" +
                "1221.12\n" +
                "1221.13\n" +
                "1221.14\n" +
                "1401\n" +
                "1402\n" +
                "1403\n" +
                "1403.01\n" +
                "1403.02\n" +
                "1403.03\n" +
                "1403.04\n" +
                "1403.05\n" +
                "1403.06\n" +
                "1403.07\n" +
                "1403.08\n" +
                "1403.09\n" +
                "1403.10\n" +
                "1403.11\n" +
                "1403.12\n" +
                "1403.13\n" +
                "1403.14\n" +
                "1403.15\n" +
                "1403.16\n" +
                "1403.17\n" +
                "1403.17.01\n" +
                "1403.18\n" +
                "1403.19\n" +
                "1403.20\n" +
                "1403.21\n" +
                "1403.22\n" +
                "1403.23\n" +
                "1403.24\n" +
                "1403.25\n" +
                "1403.26\n" +
                "1403.27\n" +
                "1403.28\n" +
                "1403.29\n" +
                "1403.30\n" +
                "1403.31\n" +
                "1403.32\n" +
                "1403.33\n" +
                "1403.34\n" +
                "1403.35\n" +
                "1403.36\n" +
                "1403.37\n" +
                "1403.38\n" +
                "1403.39\n" +
                "1403.40\n" +
                "1403.41\n" +
                "1403.42\n" +
                "1403.43\n" +
                "1403.44\n" +
                "1403.45\n" +
                "1403.46\n" +
                "1403.47\n" +
                "1403.48\n" +
                "1403.49\n" +
                "1403.50\n" +
                "1403.51\n" +
                "1403.52\n" +
                "1403.53\n" +
                "1403.54\n" +
                "1403.55\n" +
                "1403.56\n" +
                "1403.57\n" +
                "1403.58\n" +
                "1403.59\n" +
                "1403.60\n" +
                "1403.61\n" +
                "1403.62\n" +
                "1403.63\n" +
                "1403.64\n" +
                "1403.65\n" +
                "1403.66\n" +
                "1403.67\n" +
                "1403.68\n" +
                "1403.112\n" +
                "1403.113\n" +
                "1403.114\n" +
                "1403.115\n" +
                "1403.116\n" +
                "1403.117\n" +
                "1403.118\n" +
                "1403.119\n" +
                "1404\n" +
                "1405\n" +
                "1405.01\n" +
                "1405.02\n" +
                "1405.03\n" +
                "1405.04\n" +
                "1405.05\n" +
                "1405.06\n" +
                "1407\n" +
                "1408\n" +
                "1411\n" +
                "1412\n" +
                "1413\n" +
                "1421\n" +
                "1501\n" +
                "1511\n" +
                "1601\n" +
                "1601.01\n" +
                "1601.02\n" +
                "1601.03\n" +
                "1601.04\n" +
                "1601.05\n" +
                "1601.06\n" +
                "1601.07\n" +
                "1601.08\n" +
                "1601.09\n" +
                "1601.10\n" +
                "1601.11\n" +
                "1601.12\n" +
                "1601.13\n" +
                "1601.14\n" +
                "1601.15\n" +
                "1601.16\n" +
                "1601.17\n" +
                "1601.18\n" +
                "1601.19\n" +
                "1601.20\n" +
                "1601.21\n" +
                "1601.22\n" +
                "1601.23\n" +
                "1601.24\n" +
                "1601.25\n" +
                "1601.26\n" +
                "1601.27\n" +
                "1601.28\n" +
                "1601.29\n" +
                "1601.30\n" +
                "1601.31\n" +
                "1601.32\n" +
                "1601.33\n" +
                "1601.34\n" +
                "1601.35\n" +
                "1601.36\n" +
                "1601.37\n" +
                "1601.38\n" +
                "1601.39\n" +
                "1601.40\n" +
                "1601.41\n" +
                "1601.42\n" +
                "1601.43\n" +
                "1601.44\n" +
                "1601.45\n" +
                "1601.46\n" +
                "1601.47\n" +
                "1601.48\n" +
                "1601.49\n" +
                "1601.50\n" +
                "1601.51\n" +
                "1601.52\n" +
                "1601.53\n" +
                "1601.54\n" +
                "1601.63\n" +
                "1601.64\n" +
                "1601.65\n" +
                "1601.66\n" +
                "1601.67\n" +
                "1602\n" +
                "1604\n" +
                "1605\n" +
                "1606\n" +
                "1621\n" +
                "1622\n" +
                "1701\n" +
                "1702\n" +
                "1702.01\n" +
                "1801\n" +
                "1901\n" +
                "2001\n" +
                "2001.01\n" +
                "2001.05\n" +
                "2001.06\n" +
                "2001.07\n" +
                "2201\n" +
                "2202\n" +
                "2202.01\n" +
                "2202.02\n" +
                "2202.03\n" +
                "2202.04\n" +
                "2202.05\n" +
                "2202.06\n" +
                "2202.07\n" +
                "2202.08\n" +
                "2202.09\n" +
                "2202.10\n" +
                "2202.11\n" +
                "2202.12\n" +
                "2202.13\n" +
                "2202.14\n" +
                "2202.15\n" +
                "2202.16\n" +
                "2202.17\n" +
                "2202.18\n" +
                "2202.19\n" +
                "2202.20\n" +
                "2202.21\n" +
                "2202.22\n" +
                "2202.23\n" +
                "2202.24\n" +
                "2202.25\n" +
                "2202.26\n" +
                "2202.27\n" +
                "2202.28\n" +
                "2202.29\n" +
                "2202.30\n" +
                "2202.31\n" +
                "2202.32\n" +
                "2202.33\n" +
                "2202.34\n" +
                "2202.35\n" +
                "2202.36\n" +
                "2202.37\n" +
                "2202.38\n" +
                "2202.39\n" +
                "2202.40\n" +
                "2202.41\n" +
                "2202.42\n" +
                "2202.43\n" +
                "2202.44\n" +
                "2202.45\n" +
                "2202.46\n" +
                "2202.47\n" +
                "2202.48\n" +
                "2202.49\n" +
                "2202.50\n" +
                "2202.51\n" +
                "2202.52\n" +
                "2202.53\n" +
                "2202.54\n" +
                "2202.55\n" +
                "2202.56\n" +
                "2202.57\n" +
                "2202.58\n" +
                "2202.59\n" +
                "2202.60\n" +
                "2202.61\n" +
                "2202.62\n" +
                "2202.63\n" +
                "2202.64\n" +
                "2202.65\n" +
                "2202.66\n" +
                "2202.67\n" +
                "2202.68\n" +
                "2202.69\n" +
                "2202.70\n" +
                "2202.71\n" +
                "2202.72\n" +
                "2202.73\n" +
                "2202.74\n" +
                "2202.75\n" +
                "2202.76\n" +
                "2202.77\n" +
                "2202.78\n" +
                "2202.79\n" +
                "2202.80\n" +
                "2202.81\n" +
                "2202.82\n" +
                "2202.83\n" +
                "2202.84\n" +
                "2202.85\n" +
                "2202.86\n" +
                "2202.87\n" +
                "2202.88\n" +
                "2202.89\n" +
                "2202.90\n" +
                "2202.91\n" +
                "2202.92\n" +
                "2202.93\n" +
                "2202.94\n" +
                "2202.95\n" +
                "2202.96\n" +
                "2202.97\n" +
                "2202.99\n" +
                "2202.100\n" +
                "2202.101\n" +
                "2202.102\n" +
                "2202.103\n" +
                "2202.104\n" +
                "2202.105\n" +
                "2202.106\n" +
                "2202.107\n" +
                "2202.108\n" +
                "2202.109\n" +
                "2202.110\n" +
                "2202.111\n" +
                "2202.112\n" +
                "2202.113\n" +
                "2202.114\n" +
                "2202.115\n" +
                "2202.116\n" +
                "2202.117\n" +
                "2202.118\n" +
                "2202.119\n" +
                "2202.120\n" +
                "2202.121\n" +
                "2202.122\n" +
                "2202.123\n" +
                "2202.124\n" +
                "2202.125\n" +
                "2202.259\n" +
                "2202.260\n" +
                "2202.261\n" +
                "2202.262\n" +
                "2202.263\n" +
                "2202.264\n" +
                "2202.265\n" +
                "2202.266\n" +
                "2202.267\n" +
                "2202.268\n" +
                "2202.269\n" +
                "2202.270\n" +
                "2202.271\n" +
                "2202.272\n" +
                "2202.273\n" +
                "2202.274\n" +
                "2202.275\n" +
                "2202.276\n" +
                "2202.277\n" +
                "2202.278\n" +
                "2202.279\n" +
                "2202.280\n" +
                "2202.281\n" +
                "2202.282\n" +
                "2202.283\n" +
                "2202.284\n" +
                "2202.285\n" +
                "2202.286\n" +
                "2202.287\n" +
                "2202.288\n" +
                "2202.289\n" +
                "2202.290\n" +
                "2202.291\n" +
                "2202.292\n" +
                "2202.293\n" +
                "2202.294\n" +
                "2202.295\n" +
                "2202.296\n" +
                "2202.297\n" +
                "2202.298\n" +
                "2202.299\n" +
                "2202.300\n" +
                "2202.301\n" +
                "2202.302\n" +
                "2202.303\n" +
                "2202.304\n" +
                "2202.305\n" +
                "2202.306\n" +
                "2202.307\n" +
                "2202.308\n" +
                "2202.309\n" +
                "2202.310\n" +
                "2202.311\n" +
                "2202.312\n" +
                "2202.313\n" +
                "2202.314\n" +
                "2202.315\n" +
                "2202.316\n" +
                "2202.317\n" +
                "2202.318\n" +
                "2202.319\n" +
                "2202.320\n" +
                "2202.321\n" +
                "2202.322\n" +
                "2202.323\n" +
                "2202.324\n" +
                "2203\n" +
                "2211\n" +
                "2211.01\n" +
                "2221\n" +
                "2221.01\n" +
                "2221.01.01\n" +
                "2221.01.01.01\n" +
                "2221.01.02\n" +
                "2221.01.03\n" +
                "2221.01.04\n" +
                "2221.01.05\n" +
                "2221.02\n" +
                "2221.03\n" +
                "2221.04\n" +
                "2221.05\n" +
                "2221.06\n" +
                "2221.07\n" +
                "2221.08\n" +
                "2221.09\n" +
                "2231\n" +
                "2232\n" +
                "2241\n" +
                "2241.01\n" +
                "2241.02\n" +
                "2241.03\n" +
                "2241.04\n" +
                "2241.04.01\n" +
                "2241.05\n" +
                "2241.10\n" +
                "2241.11\n" +
                "2241.12\n" +
                "2401\n" +
                "2501\n" +
                "2701\n" +
                "3001\n" +
                "3001.01\n" +
                "3001.02\n" +
                "3001.03\n" +
                "3002\n" +
                "3101\n" +
                "3103\n" +
                "3104\n" +
                "3104.01\n" +
                "4001\n" +
                "4001.01\n" +
                "4001.01.01\n" +
                "4001.01.02\n" +
                "4001.01.03\n" +
                "4001.01.04\n" +
                "4001.01.05\n" +
                "4002\n" +
                "4101\n" +
                "4101.01\n" +
                "4101.02\n" +
                "4101.03\n" +
                "4301\n" +
                "4401\n" +
                "4403\n" +
                "5001\n" +
                "5001.01\n" +
                "5001.01.01\n" +
                "5001.01.02\n" +
                "5001.01.03\n" +
                "5001.01.04\n" +
                "5051\n" +
                "5051.01\n" +
                "5051.01.01\n" +
                "5051.02\n" +
                "5051.03\n" +
                "5111\n" +
                "5301\n" +
                "5401\n" +
                "5401.01\n" +
                "5402\n" +
                "5403\n" +
                "5601\n" +
                "5601.01\n" +
                "5601.02\n" +
                "5601.03\n" +
                "5601.04\n" +
                "5602\n" +
                "5602.01\n" +
                "5602.02\n" +
                "5602.03\n" +
                "5602.04\n" +
                "5602.05\n" +
                "5602.06\n" +
                "5602.07\n" +
                "5602.08\n" +
                "5602.09\n" +
                "5602.10\n" +
                "5602.11\n" +
                "5602.12\n" +
                "5602.13\n" +
                "5602.14\n" +
                "5602.15\n" +
                "5602.16\n" +
                "5602.17\n" +
                "5603\n" +
                "5603.01\n" +
                "5603.02\n" +
                "5711\n" +
                "5711.01\n" +
                "5801\n" +
                "5901\n" +
                "合计\n" +
                "单位（元） 币种（人民币）";

        String[] split = str.split("\n");
        //记录英文字母序号1-9
        Map<String, Integer> numMap = new HashMap();
        //存放当前使用的英文字母
        Map<String, String> englishLetterMap = new HashMap();
        List<String> list = Arrays.asList("A", "B", "C", "D", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z");
        int englishLetter = 0;
        for (int i = 0; i < split.length; i++) {
            StringBuilder stringBuilder = new StringBuilder();
            if (split[i].indexOf(".") > -1) {
                String[] bmArray = split[i].split("\\.");
                stringBuilder.append(bmArray[0]);
                for (int j = 1;j < bmArray.length; j++){
                    if(bmArray[j].length() > 2){
                        if(englishLetterMap.get(bmArray[0]+j) != null){
                            String englishCode = englishLetterMap.get(bmArray[0]+j);
                            stringBuilder.append(englishCode+(numMap.get(bmArray[0]+j)+1));
                            numMap.put(bmArray[0]+j,numMap.get(bmArray[0]+j)+1);
                            if(numMap.get(bmArray[0]+j) > 8){
                                englishLetter++ ;
                                englishLetterMap.put(bmArray[0]+j,list.get(englishLetter));
                                numMap.put(bmArray[0]+j,0);
                            }
                        }else{
                            englishLetter = 0;
                            englishLetterMap.put(bmArray[0]+j,list.get(englishLetter));
                            numMap.put(bmArray[0]+j,1);
                            stringBuilder.append(list.get(englishLetter)+1);
                        }
                    }else{
                        stringBuilder.append(bmArray[j]);
                    }
                }
            } else {
                stringBuilder.append(split[i]);
            }

            System.out.println(stringBuilder.toString());
        }
    }
}