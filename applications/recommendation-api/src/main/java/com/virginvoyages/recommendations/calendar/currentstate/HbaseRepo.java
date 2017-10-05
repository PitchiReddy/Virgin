package com.virginvoyages.recommendations.calendar.currentstate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.security.UserGroupInformation;
import org.springframework.stereotype.Repository;



@Repository
public class HbaseRepo {

	//Configuration configuration = HBaseConfiguration.create();


	public void createTable() throws IOException{

		Configuration configuration = HBaseConfiguration.create();

		//String POSTS_TABLE = "emp";
		//String MESSAGE_COLUMN_FAMILTY="personal";

		//		configuration.set("hbase.zookeeper.quorum","vvhwdevnode1.aws.virginvoyages.com,vvhwdevnode2.aws.virginvoyages.com,vvhwdevnode3.aws.virginvoyages.com");
		//		configuration.set("hbase.zookeeper.property.clientPort", "2181");
		//		configuration.set("zookeeper.znode.parent","/hbase-secure");
		//		configuration.set("hbase.master.kerberos.principal", "hbase/vvhwdevnode1.aws.virginvoyages.com@VVHWAD.AWS.VIRGINVOYAGES.COM");
		//		configuration.set("hbase.regionserver.kerberos.principal", "hbase/vvhwdevnode1.aws.virginvoyages.com@VVHWAD.AWS.VIRGINVOYAGES.COM");
		//		configuration.set("hbase.master.keytab.file", "D://Users//chverma//Desktop//hbase.service.keytab");
		//		configuration.set("hbase.regionserver.keytab.file", "D://Users//chverma//Desktop//hbase.service.keytab");

		configuration.set("hadoop.security.authentication", "Kerberos");
		UserGroupInformation.setConfiguration(configuration);
		UserGroupInformation.loginUserFromKeytab("hbase/vvhwdevnode1.aws.virginvoyages.com@VVHWAD.AWS.VIRGINVOYAGES.COM", "D://Users//chverma//Desktop//hbase.service.keytab");
		//UserGroupInformation.loginUserFromKeytab("chverma@VVHWAD.AWS.VIRGINVOYAGES.COM", "D://Scala-Workspace//chverma.headless.keytab");



		// Instantiating HbaseAdmin class
		//	      HBaseAdmin admin = new HBaseAdmin(configuration);
		//
		//	      // Instantiating table descriptor class
		//	      HTableDescriptor tableDescriptor = new
		//	      HTableDescriptor(TableName.valueOf("sailor"));
		//
		//	      // Adding column families to table descriptor
		//	      tableDescriptor.addFamily(new HColumnDescriptor("personal"));
		//	      tableDescriptor.addFamily(new HColumnDescriptor("tribe"));
		//
		//	      // Execute the table through admin
		//	      admin.createTable(tableDescriptor);
		//	      System.out.println(" Table created ");



		// Instantiating HTable class
		HTable hTable = new HTable(configuration, "sailor");

		// Instantiating Put class
		// accepts a row name.
		Put p = new Put(Bytes.toBytes("2")); 

		// adding values using add() method
		// accepts column family name, qualifier/row name ,value
		p.add(Bytes.toBytes("personal"),
				Bytes.toBytes("name"),Bytes.toBytes("Alpesh"));

		p.add(Bytes.toBytes("personal"),
				Bytes.toBytes("city"),Bytes.toBytes("Atlanta"));

		p.add(Bytes.toBytes("tribe"),Bytes.toBytes("tribe_name"),
				Bytes.toBytes("Explore"));

		p.add(Bytes.toBytes("tribe"),Bytes.toBytes("sub_tribe"),
				Bytes.toBytes("Mystery"));

		// Saving the put Instance to the HTable.
		hTable.put(p);
		System.out.println("data inserted");

		// closing HTable
		hTable.close();


	}




	public List<String> listData() throws IOException {
		//		System.setProperty("java.security.auth.login.config", "D:\\keytab\\hbase_jaas.conf");
		//
		//		System.setProperty("javax.security.auth.useSubjectCredsOnly", "false");
		//
		//		System.setProperty("sun.security.krb5.debug", "true");
		List<String> list1 = new ArrayList<String>();
		Configuration configuration = HBaseConfiguration.create();

		//String POSTS_TABLE = "emp";
		//String MESSAGE_COLUMN_FAMILTY="personal";

		//		configuration.set("hbase.zookeeper.quorum","vvhwdevnode1.aws.virginvoyages.com,vvhwdevnode2.aws.virginvoyages.com,vvhwdevnode3.aws.virginvoyages.com");
		//		configuration.set("hbase.zookeeper.property.clientPort", "2181");
		//		configuration.set("zookeeper.znode.parent","/hbase-secure");
		//		configuration.set("hbase.master.kerberos.principal", "hbase/vvhwdevnode1.aws.virginvoyages.com@VVHWAD.AWS.VIRGINVOYAGES.COM");
		//		configuration.set("hbase.regionserver.kerberos.principal", "hbase/vvhwdevnode1.aws.virginvoyages.com@VVHWAD.AWS.VIRGINVOYAGES.COM");
		//		configuration.set("hbase.master.keytab.file", "D://Users//chverma//Desktop//hbase.service.keytab");
		//		configuration.set("hbase.regionserver.keytab.file", "D://Users//chverma//Desktop//hbase.service.keytab");

		configuration.set("hadoop.security.authentication", "Kerberos");
		UserGroupInformation.setConfiguration(configuration);
		UserGroupInformation.loginUserFromKeytab("hbase/vvhwdevnode1.aws.virginvoyages.com@VVHWAD.AWS.VIRGINVOYAGES.COM", "D://Users//chverma//Desktop//hbase.service.keytab");
		//UserGroupInformation.loginUserFromKeytab("chverma@VVHWAD.AWS.VIRGINVOYAGES.COM", "D://Scala-Workspace//chverma.headless.keytab");


		Connection connection = ConnectionFactory.createConnection(configuration);
		Table table = connection.getTable(TableName.valueOf("emp"));

		Scan scan1 = new Scan();
		ResultScanner scanner1 = table.getScanner(scan1);

		for (Result res : scanner1) {
			System.out.println(Bytes.toString(res.getRow()));
			System.out.println(Bytes.toString(res.getValue("personal".getBytes(), "name".getBytes())));
			System.out.println(Bytes.toString(res.getValue("personal".getBytes(), "city".getBytes())));
			list1.add(Bytes.toString(res.getRow()));
			list1.add(Bytes.toString(res.getValue("personal".getBytes(), "name".getBytes())));
			list1.add(Bytes.toString(res.getValue("personal".getBytes(), "city".getBytes())));
			list1.add(Bytes.toString(res.getValue("professional".getBytes(), "role".getBytes())));
			list1.add(Bytes.toString(res.getValue("professional".getBytes(), "salary".getBytes())));
			//			bean_data.setName(Bytes.toString(res.getValue("personal".getBytes(), "name".getBytes())));
			//			bean_data.setCity(Bytes.toString(res.getValue("personal".getBytes(), "city".getBytes())));
			//			bean_data.setRole(Bytes.toString(res.getValue("professional".getBytes(), "role".getBytes())));
			//			bean_data.setSalary(Bytes.toString(res.getValue("professional".getBytes(), "salary".getBytes())));

		}

		scanner1.close();
		connection.close();
		return list1;



	}

	public TableBean getSingleRow(String sailorID) throws IOException {

		Configuration configuration = HBaseConfiguration.create();
		configuration.set("hbase.zookeeper.quorum","ip-10-3-100-150.ec2.internal");
		configuration.set("hbase.zookeeper.property.clientPort", "2181");
		//configuration.set("hadoop.security.authentication", "Kerberos");
		//		UserGroupInformation.setConfiguration(configuration);
		//		UserGroupInformation.loginUserFromKeytab("hbase/vvhwdevnode1.aws.virginvoyages.com@VVHWAD.AWS.VIRGINVOYAGES.COM", "D://Users//chverma//Desktop//hbase.service.keytab");


		Connection connection = ConnectionFactory.createConnection(configuration);
		Table table = connection.getTable(TableName.valueOf("sailor") );


		Get g = new Get(Bytes.toBytes(sailorID));

		// Reading the data
		Result result = table.get(g);

		// Reading values from Result class object
		byte [] value = result.getValue(Bytes.toBytes("personal"),Bytes.toBytes("name"));

		byte [] value1 = result.getValue(Bytes.toBytes("personal"),Bytes.toBytes("city"));

		byte [] value2 = result.getValue(Bytes.toBytes("tribe"),Bytes.toBytes("tribe_name"));

		byte [] value3 = result.getValue(Bytes.toBytes("tribe"),Bytes.toBytes("sub_tribe"));


		TableBean hBasebean = new TableBean();	      
		// Printing the values
		String name = Bytes.toString(value);
		String city = Bytes.toString(value1);
		String tribe_name = Bytes.toString(value2);
		String sub_tribe =  Bytes.toString(value3);

		System.out.println("name: " + name + " city: " + city);

		hBasebean.setName(name);
		hBasebean.setCity(city);
		hBasebean.setTribe_name(tribe_name);
		hBasebean.setSub_tribe(sub_tribe);

		return hBasebean;


	}

	public List<CalendarBean> getCalendarRecomendation(String[] resID, String[] sailorID) throws IOException {

		List<CalendarBean> hBasebeanList = new ArrayList<CalendarBean>();
		Configuration configuration = HBaseConfiguration.create();
		configuration.set("hbase.zookeeper.quorum","ip-10-3-100-150.ec2.internal");
		configuration.set("hbase.zookeeper.property.clientPort", "2181");

		Connection connection = ConnectionFactory.createConnection(configuration);
		Table table = connection.getTable(TableName.valueOf("calendar_api"));

		//		Calendar calendar = Calendar.getInstance(); // this would default to now
		//		calendar.add(Calendar.DAY_OF_MONTH, -dateRange);
		//		
		//		long startTime = calendar.getTimeInMillis();

		//		System.out.println(startTime);

		System.out.println(System.currentTimeMillis());
		Scan scan1 =  new Scan();

		FilterList fList = new FilterList(FilterList.Operator.MUST_PASS_ONE);


		System.out.println("Size of Array " + resID.length);

		for(int i=0;i<resID.length;i++)
		{	
			for(int j = 0 ; j <sailorID.length;j++)
			{
				System.out.println("ROWKEY"+resID[i]+sailorID[j]);
				fList.addFilter(new PrefixFilter(Bytes.toBytes(resID[i]+"+"+sailorID[j])));
			}
		}
		//		

		

		scan1.setFilter(fList);

		//scan1.setFilter(fListSailor);
		ResultScanner scanner1 = table.getScanner(scan1);

		for (Result res : scanner1) {
			System.out.println(Bytes.toString(res.getRow()));
			System.out.println(Bytes.toString(res.getValue("reservation".getBytes(), "reservation_id".getBytes())));
			System.out.println(Bytes.toString(res.getValue("reservation".getBytes(), "sailor_id".getBytes())));

			CalendarBean hBasebean = new CalendarBean() ;
			
			hBasebean.setNbxUniqueKey(Bytes.toString(res.getRow()));
			
			hBasebean.setReservation_id(Bytes.toString(res.getValue("reservation".getBytes(), "reservation_id".getBytes())));
			hBasebean.setSailor_id(Bytes.toString(res.getValue("reservation".getBytes(), "sailor_id".getBytes())));

			hBasebean.setActivity_id(Bytes.toString(res.getValue("activity".getBytes(), "activity_id".getBytes())));
			hBasebean.setCategory(Bytes.toString(res.getValue("activity".getBytes(), "category".getBytes())));
			hBasebean.setStart_datetime(Bytes.toString(res.getValue("activity".getBytes(), "start_datetime".getBytes())));
			hBasebean.setEnd_datetime(Bytes.toString(res.getValue("activity".getBytes(), "end_datetime".getBytes())));
			hBasebean.setStatus(Bytes.toString(res.getValue("activity".getBytes(), "status".getBytes())));
			hBasebean.setDescription(Bytes.toString(res.getValue("activity".getBytes(), "description".getBytes())));
			hBasebean.setType(Bytes.toString(res.getValue("activity".getBytes(), "type".getBytes())));
			hBasebean.setRanking(Bytes.toString(res.getValue("activity".getBytes(), "ranking".getBytes())));
			
			hBasebeanList.add(hBasebean);
		}

		scanner1.close();
		connection.close();

		if(hBasebeanList.size()==0)
		{
			return null;
		}
		else
		{
		return  hBasebeanList;
		}


	}


}
