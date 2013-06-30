package org.geese.ci.classifier.util;

import org.geese.util.LogUtil;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import org.geese.ci.classifier.Classifier;
import org.geese.ci.classifier.TrainException;

public class TrainUtil {

	private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

	public static void train(Classifier cl) throws TrainException {
		cl.train("Nobady owns the water.", "good");
		cl.train("the quick rabbit jumps fences.", "good");
		cl.train("buy pharmaceuticals now", "bad");
		cl.train("make quick money at the online casino", "bad");
		cl.train("the quick brown fox jumps", "good");
	}

	public static void train(Classifier cl, String categoryName, String fileName, Charset charset)
		throws TrainException {
		Path path = FileSystems.getDefault().getPath(fileName);
		try {
			byte[] bytes = Files.readAllBytes(path);
			String sentense = new String(bytes, charset);
			cl.train(sentense, categoryName);
		} catch (IOException ex) {
			LogUtil.error(ex);
			throw new TrainException(ex.getMessage());
		}
	}

	public static void train(Classifier cl, String categoryName, String fileName)
		throws TrainException {
		train(cl, categoryName, fileName, DEFAULT_CHARSET);
	}
}
