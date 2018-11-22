import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class ProjectReducer extends Reducer<Text, DoubleWritable, Text, Text> {

  @Override
  public void reduce(Text key, Iterable<DoubleWritable> values, Context context)
      throws IOException, InterruptedException {

	  double sumRating = 0;
	  int count = 0;

		// Go through all values to sum up card values for a card suit
		for (DoubleWritable value : values) {
			sumRating += value.get();
			count++;
		}
		double avgRating = sumRating / count;
		String result = avgRating + "\t" + count + "\t";
		context.write(key, new Text(result));
  }
}