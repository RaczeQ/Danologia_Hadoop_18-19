import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class ProjectDriver extends Configured implements Tool {

	@Override
	public int run(String[] args) throws Exception {
		String input1, input2, output;
		if (args.length == 3) {
			input1 = args[0];
			input2 = args[1];
			output = args[2];
		} else {
			System.err.println("Incorrect number of arguments.  Expected: input1 input2 output");
			return -1;
		}

		Job job = new Job(getConf());
		job.setJarByClass(ProjectDriver.class);
		job.setJobName(this.getClass().getName());

//		FileInputFormat.setInputPaths(job, new Path(input));
		FileOutputFormat.setOutputPath(job, new Path(output));
		
		MultipleInputs.addInputPath(job, new Path(input1), TextInputFormat.class, ProjectMapper.class);
		MultipleInputs.addInputPath(job, new Path(input2), TextInputFormat.class, ProjectNameMapper.class);

//		job.setMapperClass(ProjectMapper.class);
		job.setReducerClass(ProjectReducer.class);

//		job.setMapOutputKeyClass(Text.class);
//		job.setMapOutputValueClass(DoubleWritable.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		boolean success = job.waitForCompletion(true);
		return success ? 0 : 1;
	}

	public static void main(String[] args) throws Exception {
		ProjectDriver driver = new ProjectDriver();
		int exitCode = ToolRunner.run(driver, args);
		System.exit(exitCode);
	}
}

