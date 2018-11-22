import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class ProjectNameMapper extends Mapper<LongWritable, Text, Text, Text> {
	
	private static Pattern inputPattern = Pattern.compile("([0-9]+),(.*),(.*)");
	private static String fileTag = "N~";

  @Override
  public void map(LongWritable key, Text value, Context context)
      throws IOException, InterruptedException {		
		String inputLine = value.toString();

		Matcher inputMatch = inputPattern.matcher(inputLine);

		// Use regex to throw out first line of csv
		if (inputMatch.matches()) {
			// Normalize inconsistent case for card suits
			String movieId = inputMatch.group(1).toLowerCase();
			String movieName = inputMatch.group(2);

			context.write(new Text(movieId), new Text(fileTag+movieName));
		}
  }
}
