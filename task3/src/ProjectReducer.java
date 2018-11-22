import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class ProjectReducer extends Reducer<Text, Text, Text, Text> {

  @Override
  public void reduce(Text key, Iterable<Text> values, Context context)
      throws IOException, InterruptedException {

	  double sumRating = 0;
	  int count = 0;
	  String movieName = "DEFAULT";

		// Go through all values to sum up card values for a card suit
		for (Text value : values) {
			String valueSplitted[] = value.toString().split("~");
			if(valueSplitted[0].equals("R"))
            {
				sumRating += Double.parseDouble(valueSplitted[1]);
				count++;
            }
            else if(valueSplitted[0].equals("N"))
            {
            	movieName = valueSplitted[1];
            }
		}
		double avgRating = sumRating / count;
		String result = movieName + "\t" + avgRating + "\t" + count + "\t";
		context.write(key, new Text(result));
  }
}