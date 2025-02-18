INSERT INTO [grabit].Users (
	GitHubID,
	JoinedAt
	)
VALUES (
	'johnDoe',
	'2023-01-15 10:00:00'
	),
	(
	'janeSmith',
	'2023-02-20 11:30:00'
	),
	(
	'bobJones',
	'2023-03-10 09:00:00'
	),
	(
	'aliceWhite',
	'2023-04-05 14:45:00'
	),
	(
	'sarahBrown',
	'2023-05-15 16:00:00'
	),
	(
	'michaelLee',
	'2023-06-01 17:00:00'
	),
	(
	'lucasGreen',
	'2023-07-25 13:30:00'
	),
	(
	'emilyAdams',
	'2023-08-02 11:10:00'
	),
	(
	'danielClark',
	'2023-09-08 10:30:00'
	),
	(
	'rachelScott',
	'2023-10-10 08:00:00'
	),
	(
	'chrisMoore',
	'2023-11-03 12:00:00'
	),
	(
	'miaKing',
	'2023-12-15 14:20:00'
	),
	(
	'oliviaMartinez',
	'2024-01-04 09:50:00'
	),
	(
	'tylerRodriguez',
	'2024-01-15 11:00:00'
	),
	(
	'isabellaPerez',
	'2024-02-02 13:30:00'
	),
	(
	'josephYoung',
	'2024-02-10 15:45:00'
	),
	(
	'avaHall',
	'2024-03-01 16:00:00'
	),
	(
	'williamWalker',
	'2024-03-18 09:30:00'
	),
	(
	'sofiaLopez',
	'2024-04-25 10:00:00'
	),
	(
	'jacksonHarris',
	'2024-05-07 12:10:00'
	),
	(
	'lucyGonzalez',
	'2024-05-18 14:40:00'
	),
	(
	'ellaPerez',
	'2024-06-10 13:00:00'
	),
	(
	'matthewThompson',
	'2024-07-01 11:30:00'
	),
	(
	'kateEvans',
	'2024-07-10 10:40:00'
	),
	(
	'hannahCarter',
	'2024-08-15 12:20:00'
	),
	(
	'jamesMitchell',
	'2024-08-23 16:50:00'
	),
	(
	'liamRoberts',
	'2024-09-10 13:30:00'
	),
	(
	'masonMiller',
	'2024-10-02 10:20:00'
	),
	(
	'ellaDavis',
	'2024-10-12 15:15:00'
	),
	(
	'lucasWilson',
	'2024-11-05 09:50:00'
	),
	(
	'oliviaAnderson',
	'2024-11-21 14:00:00'
	),
	(
	'henryTaylor',
	'2024-12-01 11:10:00'
	),
	(
	'charlotteWhite',
	'2024-12-10 12:20:00'
	),
	(
	'elizabethMoore',
	'2025-01-03 14:30:00'
	),
	(
	'jacobJackson',
	'2025-01-15 10:10:00'
	),
	(
	'abigailLee',
	'2025-02-01 13:30:00'
	),
	(
	'danielPerez',
	'2025-02-15 09:00:00'
	),
	(
	'graceDavis',
	'2025-03-10 12:30:00'
	),
	(
	'nathanHarris',
	'2025-03-22 11:20:00'
	),
	(
	'victoriaRoberts',
	'2025-04-05 10:00:00'
	),
	(
	'michaelWalker',
	'2025-04-12 12:50:00'
	),
	(
	'emmaYoung',
	'2025-05-06 09:00:00'
	),
	(
	'jackAnderson',
	'2025-05-15 14:10:00'
	);
GO

INSERT INTO [grabit].Roles (RoleTitle)
VALUES ('Project Lead'),
	('Project Member'),
	('Task Grabber'),
	('Task Collaborator');
GO

INSERT INTO [grabit].Projects (
	ProjectName,
	ProjectDescription,
	CreatedAt,
	UpdatedAt
	)
VALUES (
	'Website Redesign',
	'A project to redesign the corporate website',
	'2023-01-10 08:00:00',
	'2023-01-20 10:00:00'
	),
	(
	'Mobile App Development',
	'Developing a mobile app for online shopping',
	'2023-02-01 09:00:00',
	NULL
	),
	(
	'E-Commerce Platform',
	'Building a platform for online selling',
	'2023-03-05 10:00:00',
	NULL
	),
	(
	'Blog Platform',
	'Developing a platform for personal blogging',
	'2023-04-01 08:00:00',
	'2023-04-20 09:00:00'
	),
	(
	'AI Chatbot',
	'Creating a chatbot using AI',
	'2023-05-01 14:00:00',
	NULL
	),
	(
	'Fitness App',
	'Developing a mobile fitness app',
	'2023-06-10 10:00:00',
	NULL
	),
	(
	'Social Media Platform',
	'Building a platform for social networking',
	'2023-07-01 15:00:00',
	NULL
	),
	(
	'Task Management Tool',
	'A tool for managing team tasks and projects',
	'2023-08-01 08:30:00',
	'2023-08-10 09:15:00'
	),
	(
	'Food Delivery App',
	'Developing a food ordering and delivery mobile app',
	'2023-09-01 12:00:00',
	'2023-09-10 14:45:00'
	),
	(
	'Real Estate Platform',
	'Building an online marketplace for real estate',
	'2023-10-01 13:30:00',
	NULL
	),
	(
	'Online Learning Platform',
	'Creating a platform for online courses and education',
	'2023-11-01 14:00:00',
	NULL
	);
GO

INSERT INTO [grabit].TaskPoints (
	TaskDifficulty,
	TaskPointID
	)
VALUES (
	'Easy'
	5,
	),
	(
	'Medium',
	10
	),
	(
	'Hard',
	15
	);
GO

INSERT INTO [grabit].TaskStatus (StatusName)
VALUES ('Available'),
	('Grabbed'),
	('Review'),
	('Complete');
GO

INSERT INTO [grabit].Tasks (
	ProjectID,
	TaskPointID,
	TaskStatusID,
	TaskName,
	TaskDescription,
	TaskDeadline,
	TaskCreatedAt,
	TaskUpdatedAt,
	TaskReviewRequestedAt
	)
VALUES (
	1,
	'Medium',
	1,
	'Design homepage',
	'Create the homepage layout and design',
	'2023-02-01 12:00:00',
	'2023-01-12 09:00:00',
	'2023-01-18 10:00:00',
	NULL
	),
	(
	1,
	'Hard',
	1,
	'Build navigation menu',
	'Develop the main navigation menu for the website',
	'2023-02-05 16:00:00',
	'2023-01-13 11:30:00',
	'2023-01-19 12:15:00',
	NULL
	),
	(
	1,
	'Hard',
	2,
	'Footer design',
	'Design the footer section of the homepage',
	'2023-02-10 14:00:00',
	'2023-01-15 10:00:00',
	'2023-02-06 11:00:00',
	'2023-02-05 14:00:00'
	),
	(
	1,
	'Medium',
	1,
	'About Us page',
	'Create the layout for the About Us page',
	'2023-02-25 17:00:00',
	'2023-01-20 12:30:00',
	'2023-01-28 10:00:00',
	NULL
	);
GO

INSERT INTO [grabit].Tasks (
	ProjectID,
	TaskPointID,
	TaskStatusID,
	TaskName,
	TaskDescription,
	TaskDeadline,
	TaskCreatedAt,
	TaskUpdatedAt,
	TaskReviewRequestedAt
	)
VALUES (
	2,
	'Easy',
	2,
	'App login screen',
	'Design and develop the login screen for the app',
	'2023-02-25 09:00:00',
	'2023-02-02 09:30:00',
	'2023-02-06 10:00:00',
	'2023-02-05 10:00:00'
	),
	(
	2,
	'Medium',
	3,
	'App dashboard',
	'Design the main dashboard screen for the mobile app',
	'2023-03-10 10:00:00',
	'2023-02-10 11:15:00',
	'2023-02-22 12:10:00',
	NULL
	),
	(
	2,
	'Hard',
	1,
	'Splash screen design',
	'Design the splash screen for the mobile app',
	'2023-03-01 10:00:00',
	'2023-02-08 09:00:00',
	'2023-02-14 11:30:00',
	NULL
	),
	(
	2,
	'Easy',
	3,
	'App settings page',
	'Design the settings page for the app',
	'2023-03-15 12:00:00',
	'2023-02-12 13:00:00',
	'2023-02-18 14:00:00',
	NULL
	);
GO

INSERT INTO [grabit].Tasks (
	ProjectID,
	TaskPointID,
	TaskStatusID,
	TaskName,
	TaskDescription,
	TaskDeadline,
	TaskCreatedAt,
	TaskUpdatedAt,
	TaskReviewRequestedAt
	)
VALUES (
	3,
	'Medium',
	1,
	'Product page layout',
	'Design the layout of the product detail page',
	'2023-03-20 14:00:00',
	'2023-03-06 10:00:00',
	'2023-03-12 09:30:00',
	NULL
	),
	(
	3,
	'Medium',
	3,
	'Payment gateway integration',
	'Integrate payment gateways into the platform',
	'2023-04-05 16:00:00',
	'2023-03-12 11:00:00',
	'2023-03-20 13:00:00',
	NULL
	),
	(
	3,
	'Easy',
	2,
	'Product filtering feature',
	'Add product filtering functionality',
	'2023-04-10 14:00:00',
	'2023-03-18 09:30:00',
	'2023-03-25 10:00:00',
	'2023-03-24 10:00:00'
	);
GO

INSERT INTO [grabit].Tasks (
	ProjectID,
	TaskPointID,
	TaskStatusID,
	TaskName,
	TaskDescription,
	TaskDeadline,
	TaskCreatedAt,
	TaskUpdatedAt,
	TaskReviewRequestedAt
	)
VALUES (
	4,
	'Easy',
	3,
	'User profile design',
	'Design the user profile page for the blog platform',
	'2023-04-10 10:00:00',
	'2023-04-01 09:00:00',
	'2023-04-05 10:00:00',
	NULL
	),
	(
	4,
	'Hard',
	1,
	'Content creation feature',
	'Create the content creation interface for users',
	'2023-04-25 12:00:00',
	'2023-04-10 14:30:00',
	'2023-04-12 10:00:00',
	NULL
	),
	(
	4,
	'Medium',
	2,
	'Search functionality',
	'Implement search functionality for blog posts',
	'2023-04-30 10:00:00',
	'2023-04-20 15:00:00',
	'2023-04-26 11:30:00',
	'2023-04-25 11:30:00'
	);
GO

INSERT INTO [grabit].Tasks (
	ProjectID,
	TaskPointID,
	TaskStatusID,
	TaskName,
	TaskDescription,
	TaskDeadline,
	TaskCreatedAt,
	TaskUpdatedAt,
	TaskReviewRequestedAt
	)
VALUES (
	5,
	'Hard',
	1,
	'AI model training',
	'Train the AI model for chatbot functionality',
	'2023-06-15 12:00:00',
	'2023-05-01 13:00:00',
	'2023-05-10 14:00:00',
	NULL
	),
	(
	5,
	'Medium',
	2,
	'Chatbot user interface',
	'Design chatbot user interface',
	'2023-07-10 16:00:00',
	'2023-06-15 11:00:00',
	'2023-06-21 12:15:00',
	'2023-06-20 12:15:00'
	),
	(
	5,
	'Hard',
	3,
	'Integrate speech recognition',
	'Integrate speech-to-text into chatbot',
	'2023-07-05 15:00:00',
	'2023-06-01 14:00:00',
	'2023-06-06 16:30:00',
	NULL
	);
GO

INSERT INTO [grabit].Tasks (
	ProjectID,
	TaskPointID,
	TaskStatusID,
	TaskName,
	TaskDescription,
	TaskDeadline,
	TaskCreatedAt,
	TaskUpdatedAt,
	TaskReviewRequestedAt
	)
VALUES (
	6,
	'Easy',
	2,
	'User profile setup',
	'Setup the user profile screen for the fitness app',
	'2023-06-30 14:00:00',
	'2023-06-10 11:00:00',
	'2023-06-16 13:30:00',
	NULL
	),
	(
	6,
	'Easy',
	2,
	'Workout logging feature',
	'Develop a feature for logging user workouts',
	'2023-07-25 14:00:00',
	'2023-07-05 10:00:00',
	'2023-07-11 11:00:00',
	NULL
	),
	(
	6,
	'Hard',
	1,
	'Fitness goals page',
	'Design and develop a fitness goals tracking page',
	'2023-08-05 12:00:00',
	'2023-07-10 13:00:00',
	'2023-07-12 14:30:00',
	NULL
	);
GO

INSERT INTO [grabit].Tasks (
	ProjectID,
	TaskPointID,
	TaskStatusID,
	TaskName,
	TaskDescription,
	TaskDeadline,
	TaskCreatedAt,
	TaskUpdatedAt,
	TaskReviewRequestedAt
	)
VALUES (
	7,
	'Medium',
	1,
	'Profile page design',
	'Create the user profile page design for the social platform',
	'2023-07-15 10:00:00',
	'2023-07-01 09:00:00',
	'2023-07-05 12:00:00',
	NULL
	),
	(
	7,
	'Easy',
	2,
	'Post creation feature',
	'Develop the post creation page',
	'2023-08-01 12:00:00',
	'2023-07-10 09:00:00',
	'2023-07-13 15:00:00',
	NULL
	),
	(
	7,
	'Medium',
	3,
	'Create user feed',
	'Design the user feed feature',
	'2023-07-20 10:00:00',
	'2023-07-05 13:00:00',
	'2023-07-08 14:00:00',
	'2023-07-07 14:00:00'
	);
GO

INSERT INTO [grabit].Tasks (
	ProjectID,
	TaskPointID,
	TaskStatusID,
	TaskName,
	TaskDescription,
	TaskDeadline,
	TaskCreatedAt,
	TaskUpdatedAt,
	TaskReviewRequestedAt
	)
VALUES (
	8,
	'Medium',
	1,
	'Task filtering',
	'Implement task filtering functionality',
	'2023-08-15 11:00:00',
	'2023-08-01 10:00:00',
	'2023-08-06 12:00:00',
	NULL
	),
	(
	8,
	'Hard',
	2,
	'Assigning tasks feature',
	'Create a feature to assign tasks to users',
	'2023-08-20 13:00:00',
	'2023-08-05 14:00:00',
	'2023-08-08 10:30:00',
	'2023-08-07 10:30:00'
	);
GO

INSERT INTO [grabit].Tasks (
	ProjectID,
	TaskPointID,
	TaskStatusID,
	TaskName,
	TaskDescription,
	TaskDeadline,
	TaskCreatedAt,
	TaskUpdatedAt,
	TaskReviewRequestedAt
	)
VALUES (
	9,
	'Medium',
	1,
	'Order history page',
	'Design the order history page for users',
	'2023-09-10 11:00:00',
	'2023-09-01 13:00:00',
	'2023-09-06 14:30:00',
	NULL
	),
	(
	9,
	'Easy',
	3,
	'Restaurant profile page',
	'Design the profile page for restaurant partners',
	'2023-09-15 15:00:00',
	'2023-09-01 13:00:00',
	'2023-09-06 14:00:00',
	NULL
	),
	(
	9,
	'Medium',
	1,
	'Order tracking feature',
	'Develop the order tracking page for users',
	'2023-09-20 17:00:00',
	'2023-09-05 12:00:00',
	'2023-09-09 11:00:00',
	NULL
	);
GO

INSERT INTO [grabit].Tasks (
	ProjectID,
	TaskPointID,
	TaskStatusID,
	TaskName,
	TaskDescription,
	TaskDeadline,
	TaskCreatedAt,
	TaskUpdatedAt,
	TaskReviewRequestedAt
	)
VALUES (
	10,
	'Medium',
	1,
	'Property search functionality',
	'Develop the property search functionality',
	'2023-10-20 11:00:00',
	'2023-10-01 09:00:00',
	'2023-10-05 10:00:00',
	NULL
	),
	(
	10,
	'Hard',
	2,
	'Property details UI',
	'Design the property details page UI',
	'2023-10-30 14:00:00',
	'2023-10-10 12:30:00',
	'2023-10-16 11:30:00',
	'2023-10-15 11:30:00'
	);
GO

INSERT INTO [grabit].Tasks (
	ProjectID,
	TaskPointID,
	TaskStatusID,
	TaskName,
	TaskDescription,
	TaskDeadline,
	TaskCreatedAt,
	TaskUpdatedAt,
	TaskReviewRequestedAt
	)
VALUES (
	11,
	'Hard',
	1,
	'Course registration page',
	'Create the course registration page',
	'2023-11-15 12:00:00',
	'2023-11-01 14:00:00',
	'2023-11-06 10:30:00',
	NULL
	),
	(
	11,
	'Easy',
	3,
	'Student dashboard',
	'Design the student dashboard page',
	'2023-11-20 15:00:00',
	'2023-11-10 13:00:00',
	'2023-11-14 15:00:00',
	'2023-11-12 15:00:00'
	);
GO

INSERT INTO [grabit].ProjectCollaborators (
	UserID,
	ProjectID,
	RoleID,
	JoinedAt,
	isActive
	)
VALUES (
	1,
	1,
	1,
	'2023-01-12 08:47:21',
	1
	),
	(
	2,
	1,
	2,
	'2023-01-15 10:22:43',
	1
	),
	(
	3,
	1,
	2,
	'2023-01-18 14:38:57',
	1
	);
GO

INSERT INTO [grabit].ProjectCollaborators (
	UserID,
	ProjectID,
	RoleID,
	JoinedAt,
	isActive
	)
VALUES (
	4,
	2,
	1,
	'2023-02-01 09:17:39',
	1
	),
	(
	5,
	2,
	2,
	'2023-02-02 10:42:15',
	1
	),
	(
	6,
	2,
	2,
	'2023-02-05 12:58:44',
	1
	);
GO

INSERT INTO [grabit].ProjectCollaborators (
	UserID,
	ProjectID,
	RoleID,
	JoinedAt,
	isActive
	)
VALUES (
	7,
	3,
	1,
	'2023-03-08 09:15:37',
	1
	),
	(
	8,
	3,
	2,
	'2023-03-12 12:42:09',
	1
	),
	(
	9,
	3,
	2,
	'2023-03-15 16:08:11',
	1
	),
	(
	10,
	3,
	2,
	'2023-03-19 14:55:48',
	1
	);
GO

INSERT INTO [grabit].ProjectCollaborators (
	UserID,
	ProjectID,
	RoleID,
	JoinedAt,
	isActive
	)
VALUES (
	11,
	4,
	1,
	'2023-04-01 12:34:22',
	1
	),
	(
	12,
	4,
	2,
	'2023-04-02 13:15:54',
	1
	),
	(
	13,
	4,
	2,
	'2023-04-04 14:49:31',
	1
	),
	(
	14,
	4,
	2,
	'2023-04-06 15:23:57',
	0
	);
GO

INSERT INTO [grabit].ProjectCollaborators (
	UserID,
	ProjectID,
	RoleID,
	JoinedAt,
	isActive
	)
VALUES (
	15,
	5,
	1,
	'2023-05-01 09:04:13',
	1
	),
	(
	16,
	5,
	2,
	'2023-05-02 11:36:28',
	1
	),
	(
	17,
	5,
	2,
	'2023-05-05 14:58:47',
	1
	);
GO

INSERT INTO [grabit].ProjectCollaborators (
	UserID,
	ProjectID,
	RoleID,
	JoinedAt,
	isActive
	)
VALUES (
	18,
	6,
	1,
	'2023-06-13 07:58:12',
	1
	),
	(
	19,
	6,
	2,
	'2023-06-17 11:36:49',
	1
	),
	(
	20,
	6,
	2,
	'2023-06-21 15:19:33',
	1
	);
GO

INSERT INTO [grabit].ProjectCollaborators (
	UserID,
	ProjectID,
	RoleID,
	JoinedAt,
	isActive
	)
VALUES (
	21,
	7,
	1,
	'2023-07-01 09:05:41',
	1
	),
	(
	22,
	7,
	2,
	'2023-07-02 10:37:58',
	1
	),
	(
	23,
	7,
	2,
	'2023-07-03 11:49:23',
	1
	);
GO

INSERT INTO [grabit].ProjectCollaborators (
	UserID,
	ProjectID,
	RoleID,
	JoinedAt,
	isActive
	)
VALUES (
	24,
	8,
	1,
	'2023-08-01 10:31:46',
	1
	),
	(
	25,
	8,
	2,
	'2023-08-02 11:45:29',
	1
	),
	(
	26,
	8,
	2,
	'2023-08-03 12:07:52',
	1
	),
	(
	21,
	8,
	1,
	'2023-08-01 11:17:39',
	1
	),
	(
	27,
	8,
	2,
	'2023-08-04 12:59:23',
	0
	);
GO

INSERT INTO [grabit].ProjectCollaborators (
	UserID,
	ProjectID,
	RoleID,
	JoinedAt,
	isActive
	)
VALUES (
	28,
	9,
	1,
	'2023-09-01 13:02:58',
	1
	),
	(
	29,
	9,
	2,
	'2023-09-02 14:47:41',
	1
	),
	(
	30,
	9,
	2,
	'2023-09-03 15:39:14',
	1
	);
GO

INSERT INTO [grabit].ProjectCollaborators (
	UserID,
	ProjectID,
	RoleID,
	JoinedAt,
	isActive
	)
VALUES (
	31,
	10,
	1,
	'2023-10-01 10:07:26',
	1
	),
	(
	32,
	10,
	2,
	'2023-10-02 11:42:58',
	1
	),
	(
	33,
	10,
	2,
	'2023-10-03 12:59:31',
	0
	), 
	(
	37,
	10,
	2,
	'2023-11-04 12:29:18',
	1
	)
GO

INSERT INTO [grabit].ProjectCollaborators (
	UserID,
	ProjectID,
	RoleID,
	JoinedAt,
	isActive
	)
VALUES (
	34,
	11,
	1,
	'2023-11-01 09:14:32',
	1
	),
	(
	35,
	11,
	2,
	'2023-11-02 10:58:49',
	1
	),
	(
	36,
	11,
	2,
	'2023-11-03 11:25:37',
	1
	),
	(
	37,
	11,
	2,
	'2023-11-04 12:41:52',
	1
	);
GO

INSERT INTO [grabit].TaskCollaborators (
	UserID,
	RoleID,
	TaskID,
	JoinedAt,
	isActive
	)
VALUES (
	2,
	3,
	1,
	'2023-01-14 08:47:15',
	1
	),
	(
	3,
	4,
	1,
	'2023-01-24 16:22:43',
	1
	);
GO

INSERT INTO [grabit].TaskCollaborators (
	UserID,
	RoleID,
	TaskID,
	JoinedAt,
	isActive
	)
VALUES (
	6,
	3,
	2,
	'2023-02-09 14:05:32',
	1
	),
	(
	5,
	4,
	2,
	'2023-02-14 19:18:49',
	1
	);
GO

INSERT INTO [grabit].TaskCollaborators (
	UserID,
	RoleID,
	TaskID,
	JoinedAt,
	isActive
	)
VALUES (
	9,
	3,
	3,
	'2023-03-04 07:13:54',
	1
	),
	(
	8,
	4,
	3,
	'2023-03-09 18:39:21',
	1
	),
	(
	10,
	4,
	3,
	'2023-03-14 21:58:07',
	1
	);
GO

INSERT INTO [grabit].TaskCollaborators (
	UserID,
	RoleID,
	TaskID,
	JoinedAt,
	isActive
	)
VALUES (
	12,
	3,
	4,
	'2023-04-04 09:30:14',
	1
	),
	(
	13,
	4,
	4,
	'2023-04-06 15:20:33',
	1
	),
	(
	14,
	4,
	4,
	'2023-04-09 22:45:00',
	0
	);
GO

INSERT INTO [grabit].TaskCollaborators (
	UserID,
	RoleID,
	TaskID,
	JoinedAt,
	isActive
	)
VALUES (
	17,
	3,
	5,
	'2023-04-30 12:55:47',
	1
	),
	(
	16,
	4,
	5,
	'2023-05-09 08:40:29',
	1
	);
GO

INSERT INTO [grabit].TaskCollaborators (
	UserID,
	RoleID,
	TaskID,
	JoinedAt,
	isActive
	)
VALUES (
	19,
	3,
	6,
	'2023-05-31 07:45:56',
	1
	),
	(
	20,
	4,
	6,
	'2023-06-04 17:05:12',
	1
	);
GO

INSERT INTO [grabit].TaskCollaborators (
	UserID,
	RoleID,
	TaskID,
	JoinedAt,
	isActive
	)
VALUES (
	23,
	3,
	7,
	'2023-06-14 14:33:18',
	1
	),
	(
	22,
	4,
	7,
	'2023-06-19 09:48:52',
	1
	);
GO

-- Task 8: Task Management Tool - Task filtering
INSERT INTO [grabit].TaskCollaborators (
	UserID,
	RoleID,
	TaskID,
	JoinedAt,
	isActive
	)
VALUES (
	25,
	3,
	8,
	'2023-06-30 11:12:05',
	1
	),
	(
	26,
	4,
	8,
	'2023-07-04 08:55:43',
	1
	),
	(
	21,
	4,
	8,
	'2023-07-04 12:20:37',
	1
	),
	(
	27,
	4,
	8,
	'2023-07-06 19:05:29',
	1
	);
GO

INSERT INTO [grabit].TaskCollaborators (
	UserID,
	RoleID,
	TaskID,
	JoinedAt,
	isActive
	)
VALUES (
	29,
	3,
	9,
	'2023-07-19 16:50:00',
	1
	),
	(
	30,
	4,
	9,
	'2023-07-24 10:15:43',
	1
	);
GO

INSERT INTO [grabit].TaskCollaborators (
	UserID,
	RoleID,
	TaskID,
	JoinedAt,
	isActive
	)
VALUES (
	37,
	3,
	10,
	'2023-07-31 12:30:00',
	1
	),
	(
	32,
	4,
	10,
	'2023-08-04 14:25:19',
	1
	);
GO

INSERT INTO [grabit].TaskCollaborators (
	UserID,
	RoleID,
	TaskID,
	JoinedAt,
	isActive
	)
VALUES (
	35,
	3,
	11,
	'2023-08-09 11:05:23',
	1
	),
	(
	37,
	4,
	11,
	'2023-08-14 15:42:11',
	1
	),
	(
	36,
	4,
	11,
	'2023-08-17 18:59:31',
	1
	);
GO


