# LMS
TeachingAPP

Category
	Manage Category  --- done
	Manage Sub Categories -- to be done, first admin shd select parent category , based on parent category create child catgeories(CRUD)
Batch
	Create Batch

Assign USERS(CRUD)
	UserBatchMappingController, UserVideoCategoryMappingController
	
	Add users to batch and user to child video category(both student and tutor) --- for category we shd load childvideocategories based on parent category.
	
	Map users(Student and tutor) to Batch.
	
	Shd be able to delete users from batch or edit batch.
	
	shd be able to update users from child category or delete mapping of child category

TimeTable
	create time table
		First selct parent category, then select child video catgeory, then select video
		Select tutor, And time So time table gets created.
	Edit Time Table
	
Video
	upload video page
	home page for student to render video(based on time, video thumbnails shd get enabled)
	offline videos
