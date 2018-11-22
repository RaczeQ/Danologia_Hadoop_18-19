import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class ProjectMapper extends Mapper<LongWritable, Text, Text, DoubleWritable> {
	
	private static Pattern inputPattern = Pattern.compile("(\\d*(.\\d*)?),(\\d*(.\\d*)?),(\\d*(.\\d*)?),(\\d*(.\\d*)?)");

  @Override
  public void map(LongWritable key, Text value, Context context)
      throws IOException, InterruptedException {		
		String inputLine = value.toString();

		Matcher inputMatch = inputPattern.matcher(inputLine);

		// Use regex to throw out first line of csv
		if (inputMatch.matches()) {
			// Normalize inconsistent case for card suits
			String movieId = inputMatch.group(3).toLowerCase();
			double movieRating = Double.parseDouble(inputMatch.group(5));

			context.write(new Text(movieId), new DoubleWritable(movieRating));
		}
  }
}
