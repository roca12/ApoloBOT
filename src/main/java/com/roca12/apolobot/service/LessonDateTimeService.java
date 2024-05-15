package com.roca12.apolobot.service;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;

import com.roca12.apolobot.controller.ApplicationContextHolder;
import com.roca12.apolobot.model.LessonDateTime;
import com.roca12.apolobot.repository.LessonDateTimeRepository;

@Controller
public class LessonDateTimeService {

	private LessonDateTimeRepository lessonRepo;
	private ArrayList<LessonDateTime> all = new ArrayList<>();

	public LessonDateTimeService() {
		lessonRepo = ApplicationContextHolder.getContext().getBean(LessonDateTimeRepository.class);
	}

	public String createNewLessonDateTime(int month, int dayOfWeek, int hour, int minute, int second) {
		all = lessonRepo.findAll();
		if (all.size() == 3) {
			return "already 3 lessons";
		}
		LessonDateTime temp = new LessonDateTime(null, month, dayOfWeek, hour, minute, second);
		for (int i = 0; i < all.size(); i++) {
			LessonDateTime actual = all.get(i);
			actual.setId(null);

			if (temp.equals(actual)) {
				continue;

			} else {
				lessonRepo.save(temp);
				return "saved";
			}
		}
		return "not saved";

	}

	public void showAll() {
		lessonRepo.findAll().forEach(item -> System.out.println(item.toString()));
	}

	public void count() {
		long count = lessonRepo.count();
		System.out.println("Number of documents in the collection: " + count);
	}

}
