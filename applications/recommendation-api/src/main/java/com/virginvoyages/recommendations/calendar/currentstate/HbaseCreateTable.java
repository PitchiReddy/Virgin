package com.virginvoyages.recommendations.calendar.currentstate;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.security.UserGroupInformation;




public class HbaseCreateTable {
	Configuration configuration = HBaseConfiguration.create();

	/*public static void main5(String[] args) throws IOException {
		HbaseTest test = new HbaseTest();
		System.out.println("Calling ");
		test.createData();
	}*/
	public List<String> listData() throws IOException {
		//System.setProperty("java.security.auth.login.config", "D:\\keytab\\hbase_jaas.conf");

		//System.setProperty("javax.security.auth.useSubjectCredsOnly", "false");

		//System.setProperty("sun.security.krb5.debug", "true");
		List<String> list1 = new ArrayList<String>();
		//Configuration configuration = HBaseConfiguration.create();
		//String POSTS_TABLE = "emp";
		//String MESSAGE_COLUMN_FAMILTY="personal";

		//		configuration.set("hbase.zookeeper.quorum","vvhwdevnode1.aws.virginvoyages.com,vvhwdevnode2.aws.virginvoyages.com,vvhwdevnode3.aws.virginvoyages.com");
		//		configuration.set("hbase.zookeeper.property.clientPort", "2181");
		//		configuration.set("zookeeper.znode.parent","/hbase-secure");
		//		configuration.set("hbase.master.kerberos.principal", "hbase/vvhwdevnode1.aws.virginvoyages.com@VVHWAD.AWS.VIRGINVOYAGES.COM");
		//		configuration.set("hbase.regionserver.kerberos.principal", "hbase/vvhwdevnode1.aws.virginvoyages.com@VVHWAD.AWS.VIRGINVOYAGES.COM");
		//
		//		//Configuration conf = new Configuration();
		//configuration.set("hadoop.security.authentication", "Kerberos");
		UserGroupInformation.setConfiguration(configuration);
		//UserGroupInformation.loginUserFromKeytab("hbase/vvhwdevnode1.aws.virginvoyages.com@VVHWAD.AWS.VIRGINVOYAGES.COM", "D://Users//chverma//Desktop//hbase.service.keytab");
		//UserGroupInformation.loginUserFromKeytab("chverma@VVHWAD.AWS.VIRGINVOYAGES.COM", "D://keytabs//krb5.keytab");

		Connection connection = ConnectionFactory.createConnection(configuration);
		Table table = connection.getTable(TableName.valueOf("calendar_new"));

		Scan scan1 = new Scan();
		ResultScanner scanner1 = table.getScanner(scan1);

		for (Result res : scanner1) {
			System.out.println(Bytes.toString(res.getRow()));
			System.out.println(Bytes.toString(res.getValue("reservation".getBytes(), "reservation_id".getBytes())));
			System.out.println(Bytes.toString(res.getValue("reservation".getBytes(), "sailor_id".getBytes())));
			list1.add(Bytes.toString(res.getValue("reservation".getBytes(), "reservation_id".getBytes())));
			list1.add(Bytes.toString(res.getValue("reservation".getBytes(), "sailor_id".getBytes())));
		}

		scanner1.close();
		connection.close();
		return list1;



	}

	public void createData() throws IOException{
		UserGroupInformation.setConfiguration(configuration);


		//				 Instantiating HbaseAdmin class
		HBaseAdmin admin = new HBaseAdmin(configuration);
		//
		//			      // Instantiating table descriptor class
		HTableDescriptor tableDescriptor = new
				HTableDescriptor(TableName.valueOf("calendar_api"));
//		//
//		//			      // Adding column families to table descriptor
		tableDescriptor.addFamily(new HColumnDescriptor("reservation"));
		tableDescriptor.addFamily(new HColumnDescriptor("activity"));
//		//
//		//			      // Execute the table through admin
		admin.createTable(tableDescriptor);
		System.out.println(" Table created ");



		// Instantiating HTable class
		HTable hTable = new HTable(configuration, "calendar_api");

		try {

            File f = new File("D://HbaseData//api_data.csv");

            BufferedReader b = new BufferedReader(new FileReader(f));

            String readLine = "";

            System.out.println("Reading file using Buffered Reader");

            while ((readLine = b.readLine()) != null) {
                System.out.println(readLine);
                String data_array[] = readLine.split(",");
                
         		// Instantiating Put class
		// accepts a row name.
		Put p = new Put(Bytes.toBytes(data_array[1]+"+"+data_array[0]+"+"+data_array[2])); 

		// adding values using add() method
		// accepts column family name, qualifier/row name ,value
		p.add(Bytes.toBytes("reservation"),
				Bytes.toBytes("reservation_id"),Bytes.toBytes(data_array[1]));

		p.add(Bytes.toBytes("reservation"),
				Bytes.toBytes("sailor_id"),Bytes.toBytes(data_array[0]));

		p.add(Bytes.toBytes("activity"),Bytes.toBytes("activity_id"),
				Bytes.toBytes(data_array[2]));

		p.add(Bytes.toBytes("activity"),Bytes.toBytes("category"),
				Bytes.toBytes(data_array[3]));
		
		p.add(Bytes.toBytes("activity"),Bytes.toBytes("start_datetime"),
				Bytes.toBytes(data_array[4]));
		
		p.add(Bytes.toBytes("activity"),Bytes.toBytes("end_datetime"),
				Bytes.toBytes(data_array[5]));
		
		p.add(Bytes.toBytes("activity"),Bytes.toBytes("status"),
				Bytes.toBytes(data_array[6]));
		
		p.add(Bytes.toBytes("activity"),Bytes.toBytes("description"),
				Bytes.toBytes(data_array[7]));
		
		p.add(Bytes.toBytes("activity"),Bytes.toBytes("type"),
				Bytes.toBytes(data_array[8]));
		
		p.add(Bytes.toBytes("activity"),Bytes.toBytes("ranking"),
				Bytes.toBytes(data_array[9]));

		// Saving the put Instance to the HTable.
		hTable.put(p);
		System.out.println("data inserted");
		
            }
            b.close();
            hTable.close();// closing HTable

        } 
		catch (IOException e) {
            e.printStackTrace();
        }

	}

}
