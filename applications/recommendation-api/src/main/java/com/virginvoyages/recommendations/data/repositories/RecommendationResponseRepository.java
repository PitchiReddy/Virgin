package com.virginvoyages.recommendations.data.repositories;

import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.hbase.TableCallback;
import org.springframework.stereotype.Repository;

import com.virginvoyages.recommendations.model.RecommendationResponse;

@SuppressWarnings("deprecation")
@Repository
public class RecommendationResponseRepository {
	
	@Autowired
	private HbaseTemplate hbaseTemplate;
	
	private String tableName = "RECOMMENDATION_RESPONSE";
	
	public static byte[] CF_INFO = Bytes.toBytes("cfInfo");
	
	private byte[] qNbxUniqueKey = Bytes.toBytes("nbxUniqueKey");
	private byte[] qSailorSelection = Bytes.toBytes("sailorSelection");
	private byte[] qSelectionSentiment = Bytes.toBytes("selectionSentiment");
	
	public RecommendationResponse save(final Integer nbxUniqueKey, final String sailorSelection,
			final String selectionSentiment) {
		return hbaseTemplate.execute(tableName, new TableCallback<RecommendationResponse>() {
			public RecommendationResponse doInTable(HTableInterface table) throws Throwable {
				RecommendationResponse recommendationResponse =
						new RecommendationResponse()
							.nbxUniqueKey(nbxUniqueKey)
							.recommedation(sailorSelection)
							.selectionSentiment(selectionSentiment);
				
				String rowKey = String.valueOf(nbxUniqueKey.intValue())+"+"+System.currentTimeMillis();
				Put p = new Put(Bytes.toBytes(rowKey));
				
				p.addColumn(CF_INFO, qNbxUniqueKey, Bytes.toBytes(recommendationResponse.nbxUniqueKey().toString()));
				p.addColumn(CF_INFO, qSailorSelection, Bytes.toBytes(recommendationResponse.recommedation()));
				p.addColumn(CF_INFO, qSelectionSentiment, Bytes.toBytes(recommendationResponse.selectionSentiment()));
				table.put(p);
				
				return recommendationResponse;
				
			}
		});
	}

}
