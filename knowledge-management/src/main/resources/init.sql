create table normal_question
(
	ques_id bigint auto_increment,
	ques_topic varchar(300) null,
	ques_options varchar(300) null,
	ques_answer varchar(20) null,
	ques_type int null,
	constraint normal_question_pk
		primary key (ques_id)
);
