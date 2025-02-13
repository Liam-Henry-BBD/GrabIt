USE GrabIt
GO

-- Users (48 Users)
INSERT INTO Users (GitHubID, JoinedAt) VALUES
('johnDoe', '2023-01-15 10:00:00'),
('janeSmith', '2023-02-20 11:30:00'),
('bobJones', '2023-03-10 09:00:00'),
('aliceWhite', '2023-04-05 14:45:00'),
('sarahBrown', '2023-05-15 16:00:00'),
('michaelLee', '2023-06-01 17:00:00'),
('lucasGreen', '2023-07-25 13:30:00'),
('emilyAdams', '2023-08-02 11:10:00'),
('danielClark', '2023-09-08 10:30:00'),
('rachelScott', '2023-10-10 08:00:00'),
('chrisMoore', '2023-11-03 12:00:00'),
('miaKing', '2023-12-15 14:20:00'),
('oliviaMartinez', '2024-01-04 09:50:00'),
('tylerRodriguez', '2024-01-15 11:00:00'),
('isabellaPerez', '2024-02-02 13:30:00'),
('josephYoung', '2024-02-10 15:45:00'),
('avaHall', '2024-03-01 16:00:00'),
('williamWalker', '2024-03-18 09:30:00'),
('sofiaLopez', '2024-04-25 10:00:00'),
('jacksonHarris', '2024-05-07 12:10:00'),
('lucyGonzalez', '2024-05-18 14:40:00'),
('ellaPerez', '2024-06-10 13:00:00'),
('matthewThompson', '2024-07-01 11:30:00'),
('kateEvans', '2024-07-10 10:40:00'),
('hannahCarter', '2024-08-15 12:20:00'),
('jamesMitchell', '2024-08-23 16:50:00'),
('liamRoberts', '2024-09-10 13:30:00'),
('masonMiller', '2024-10-02 10:20:00'),
('ellaDavis', '2024-10-12 15:15:00'),
('lucasWilson', '2024-11-05 09:50:00'),
('oliviaAnderson', '2024-11-21 14:00:00'),
('henryTaylor', '2024-12-01 11:10:00'),
('charlotteWhite', '2024-12-10 12:20:00'),
('elizabethMoore', '2025-01-03 14:30:00'),
('jacobJackson', '2025-01-15 10:10:00'),
('abigailLee', '2025-02-01 13:30:00'),
('danielPerez', '2025-02-15 09:00:00'),
('graceDavis', '2025-03-10 12:30:00'),
('nathanHarris', '2025-03-22 11:20:00'),
('victoriaRoberts', '2025-04-05 10:00:00'),
('michaelWalker', '2025-04-12 12:50:00'),
('emmaYoung', '2025-05-06 09:00:00'),
('jackAnderson', '2025-05-15 14:10:00');
GO

-- Roles (4 Roles)
INSERT INTO Roles (RoleTitle) VALUES
('Project Lead'),
('Project Member'),
('Task Grabber'),
('Task Collaborator');
Go

-- Projects (11 Projects)
INSERT INTO Projects (ProjectName, ProjectDescription, CreatedAt, UpdatedAt) VALUES
('Website Redesign', 'A project to redesign the corporate website', '2023-01-10 08:00:00', '2023-01-20 10:00:00'),
('Mobile App Development', 'Developing a mobile app for online shopping', '2023-02-01 09:00:00', '2023-02-15 11:00:00'),
('E-Commerce Platform', 'Building a platform for online selling', '2023-03-05 10:00:00', '2023-03-10 12:00:00'),
('Blog Platform', 'Developing a platform for personal blogging', '2023-04-01 08:00:00', '2023-04-20 09:00:00'),
('AI Chatbot', 'Creating a chatbot using AI', '2023-05-01 14:00:00', '2023-05-15 11:30:00'),
('Fitness App', 'Developing a mobile fitness app', '2023-06-10 10:00:00', '2023-06-20 13:30:00'),
('Social Media Platform', 'Building a platform for social networking', '2023-07-01 15:00:00', '2023-07-15 16:00:00'),
('Task Management Tool', 'A tool for managing team tasks and projects', '2023-08-01 08:30:00', '2023-08-10 09:15:00'),
('Food Delivery App', 'Developing a food ordering and delivery mobile app', '2023-09-01 12:00:00', '2023-09-10 14:45:00'),
('Real Estate Platform', 'Building an online marketplace for real estate', '2023-10-01 13:30:00', '2023-10-10 11:00:00'),
('Online Learning Platform', 'Creating a platform for online courses and education', '2023-11-01 14:00:00', '2023-11-15 16:00:00');
GO

-- TaskPoints (3 Values: 5, 10, 15)
INSERT INTO TaskPoints (TaskPointID,PointValue) VALUES
('Easy',5),
('Medium',10),
('Hard',15);
Go

-- TaskStatus (4 Statuses)
INSERT INTO TaskStatus (StatusName) VALUES
('Available'),
('Grabbed'),
('Review'),
('Complete');
Go

-- Project 1: Website Redesign
INSERT INTO Tasks (ProjectID, TaskPointID, TaskStatusID, TaskName, TaskDescription, TaskDeadline, TaskCreatedAt, TaskUpdatedAt, TaskReviewRequestedAt) VALUES
(1, 'Medium', 1, 'Design homepage', 'Create the homepage layout and design', '2023-02-01 12:00:00', '2023-01-15 09:00:00', '2023-01-18 10:00:00', NULL),
(1, 'Hard', 1, 'Build navigation menu', 'Develop the main navigation menu for the website', '2023-02-05 16:00:00', '2023-01-16 11:30:00', '2023-01-19 12:15:00', NULL),
(1, 'Hard', 2, 'Footer design', 'Design the footer section of the homepage', '2023-02-10 14:00:00', '2023-01-18 10:00:00', '2023-02-06 11:00:00', '2023-02-05 14:00:00'),
(1, 'Medium', 1, 'About Us page', 'Create the layout for the About Us page', '2023-02-25 17:00:00', '2023-01-25 12:30:00', '2023-01-28 10:00:00', NULL);

-- Project 2: Mobile App Development
INSERT INTO Tasks (ProjectID, TaskPointID, TaskStatusID, TaskName, TaskDescription, TaskDeadline, TaskCreatedAt, TaskUpdatedAt, TaskReviewRequestedAt) VALUES
(2, 'Easy', 2, 'App login screen', 'Design and develop the login screen for the app', '2023-02-25 09:00:00', '2023-02-01 09:30:00', '2023-02-06 10:00:00', '2023-02-05 10:00:00'),
(2, 'Medium', 3, 'App dashboard', 'Design the main dashboard screen for the mobile app', '2023-03-10 10:00:00', '2023-02-20 11:15:00', '2023-02-22 12:10:00', NULL),
(2, 'Hard', 1, 'Splash screen design', 'Design the splash screen for the mobile app', '2023-03-01 10:00:00', '2023-02-05 09:00:00', '2023-02-07 11:30:00', NULL),
(2, 'Easy', 3, 'App settings page', 'Design the settings page for the app', '2023-03-15 12:00:00', '2023-02-10 13:00:00', '2023-02-12 14:00:00', NULL);

-- Project 3: E-Commerce Platform
INSERT INTO Tasks (ProjectID, TaskPointID, TaskStatusID, TaskName, TaskDescription, TaskDeadline, TaskCreatedAt, TaskUpdatedAt, TaskReviewRequestedAt) VALUES
(3, 'Medium', 1, 'Product page layout', 'Design the layout of the product detail page', '2023-03-15 14:00:00', '2023-03-05 10:00:00', '2023-03-07 09:30:00', NULL),
(3, 'Medium', 3, 'Payment gateway integration', 'Integrate payment gateways into the platform', '2023-04-05 16:00:00', '2023-03-10 11:00:00', '2023-03-15 13:00:00', NULL),
(3, 'Easy', 2, 'Product filtering feature', 'Add product filtering functionality', '2023-04-10 14:00:00', '2023-03-15 09:30:00', '2023-03-21 10:00:00', '2023-03-20 10:00:00');

-- Project 4: Blog Platform
INSERT INTO Tasks (ProjectID, TaskPointID, TaskStatusID, TaskName, TaskDescription, TaskDeadline, TaskCreatedAt, TaskUpdatedAt, TaskReviewRequestedAt) VALUES
(4, 'Easy', 3, 'User profile design', 'Design the user profile page for the blog platform', '2023-04-10 10:00:00', '2023-04-01 09:00:00', '2023-04-05 10:00:00', NULL),
(4, 'Hard', 1, 'Content creation feature', 'Create the content creation interface for users', '2023-04-25 12:00:00', '2023-04-10 14:30:00', '2023-04-12 10:00:00', NULL),
(4, 'Medium', 2, 'Search functionality', 'Implement search functionality for blog posts', '2023-04-30 10:00:00', '2023-04-20 15:00:00', '2023-04-26 11:30:00', '2023-04-25 11:30:00');

-- Project 5: AI Chatbot
INSERT INTO Tasks (ProjectID, TaskPointID, TaskStatusID, TaskName, TaskDescription, TaskDeadline, TaskCreatedAt, TaskUpdatedAt, TaskReviewRequestedAt) VALUES
(5, 'Hard', 1, 'AI model training', 'Train the AI model for chatbot functionality', '2023-06-15 12:00:00', '2023-05-01 13:00:00', '2023-05-10 14:00:00', NULL),
(5, 'Medium', 2, 'Chatbot user interface', 'Design chatbot user interface', '2023-07-10 16:00:00', '2023-06-15 11:00:00', '2023-06-21 12:15:00', '2023-06-20 12:15:00'),
(5, 'Hard', 3, 'Integrate speech recognition', 'Integrate speech-to-text into chatbot', '2023-07-05 15:00:00', '2023-06-01 14:00:00', '2023-06-06 16:30:00', NULL);

-- Project 6: Fitness App
INSERT INTO Tasks (ProjectID, TaskPointID, TaskStatusID, TaskName, TaskDescription, TaskDeadline, TaskCreatedAt, TaskUpdatedAt, TaskReviewRequestedAt) VALUES
(6, 'Easy', 2, 'User profile setup', 'Setup the user profile screen for the fitness app', '2023-06-30 14:00:00', '2023-06-10 11:00:00', '2023-06-16 13:30:00', NULL),
(6, 'Easy', 2, 'Workout logging feature', 'Develop a feature for logging user workouts', '2023-07-25 14:00:00', '2023-07-05 10:00:00', '2023-07-11 11:00:00', NULL),
(6, 'Hard', 1, 'Fitness goals page', 'Design and develop a fitness goals tracking page', '2023-08-05 12:00:00', '2023-07-10 13:00:00', '2023-07-12 14:30:00', NULL);

-- Project 7: Social Media Platform
INSERT INTO Tasks (ProjectID, TaskPointID, TaskStatusID, TaskName, TaskDescription, TaskDeadline, TaskCreatedAt, TaskUpdatedAt, TaskReviewRequestedAt) VALUES
(7, 'Medium', 1, 'Profile page design', 'Create the user profile page design for the social platform', '2023-07-15 10:00:00', '2023-07-01 09:00:00', '2023-07-05 12:00:00', NULL),
(7, 'Easy', 2, 'Post creation feature', 'Develop the post creation page', '2023-08-01 12:00:00', '2023-07-10 09:00:00', '2023-07-13 15:00:00', NULL),
(7, 'Medium', 3, 'Create user feed', 'Design the user feed feature', '2023-07-20 10:00:00', '2023-07-05 13:00:00', '2023-07-08 14:00:00', '2023-07-07 14:00:00');

-- Project 8: Task Management Tool
INSERT INTO Tasks (ProjectID, TaskPointID, TaskStatusID, TaskName, TaskDescription, TaskDeadline, TaskCreatedAt, TaskUpdatedAt, TaskReviewRequestedAt) VALUES
(8, 'Medium', 1, 'Task filtering', 'Implement task filtering functionality', '2023-08-15 11:00:00', '2023-08-01 10:00:00', '2023-08-06 12:00:00', NULL),
(8, 'Hard', 2, 'Assigning tasks feature', 'Create a feature to assign tasks to users', '2023-08-20 13:00:00', '2023-08-05 14:00:00', '2023-08-08 10:30:00', '2023-08-07 10:30:00');

-- Project 9: Food Delivery App
INSERT INTO Tasks (ProjectID, TaskPointID, TaskStatusID, TaskName, TaskDescription, TaskDeadline, TaskCreatedAt, TaskUpdatedAt, TaskReviewRequestedAt) VALUES
(9, 'Medium', 1, 'Order history page', 'Design the order history page for users', '2023-09-10 11:00:00', '2023-09-01 13:00:00', '2023-09-06 14:30:00', NULL),
(9, 'Easy', 3, 'Restaurant profile page', 'Design the profile page for restaurant partners', '2023-09-15 15:00:00', '2023-09-01 13:00:00', '2023-09-06 14:00:00', NULL),
(9, 'Medium', 1, 'Order tracking feature', 'Develop the order tracking page for users', '2023-09-20 17:00:00', '2023-09-05 12:00:00', '2023-09-09 11:00:00', NULL);

-- Project 10: Real Estate Platform
INSERT INTO Tasks (ProjectID, TaskPointID, TaskStatusID, TaskName, TaskDescription, TaskDeadline, TaskCreatedAt, TaskUpdatedAt, TaskReviewRequestedAt) VALUES
(10,'Medium', 1, 'Property search functionality', 'Develop the property search functionality', '2023-10-20 11:00:00', '2023-10-01 09:00:00', '2023-10-05 10:00:00', NULL),
(10, 'Hard', 2, 'Property details UI', 'Design the property details page UI', '2023-10-30 14:00:00', '2023-10-10 12:30:00', '2023-10-16 11:30:00', '2023-10-15 11:30:00');

-- Project 11: Online Learning Platform
INSERT INTO Tasks (ProjectID, TaskPointID, TaskStatusID, TaskName, TaskDescription, TaskDeadline, TaskCreatedAt, TaskUpdatedAt, TaskReviewRequestedAt) VALUES
(11, 'Hard', 1, 'Course registration page', 'Create the course registration page', '2023-11-15 12:00:00', '2023-11-01 14:00:00', '2023-11-06 10:30:00', NULL),
(11, 'Easy', 3, 'Student dashboard', 'Design the student dashboard page', '2023-11-20 15:00:00', '2023-11-10 13:00:00', '2023-11-14 15:00:00', '2023-11-12 15:00:00');

-- ProjectCollaborators (Expanded)
-- Project 1: Website Redesign
INSERT INTO ProjectCollaborators (UserID, ProjectID, RoleID, JoinedAt, isActive) VALUES
(1, 1, 1, '2023-01-01 09:00:00', 1),
(2, 1, 2, '2023-01-03 10:30:00', 1),
(3, 1, 3, '2023-01-05 11:30:00', 1);


-- Project 2: Mobile App Development
INSERT INTO ProjectCollaborators (UserID, ProjectID, RoleID, JoinedAt, isActive) VALUES
(4, 2, 1, '2023-02-01 09:00:00', 1),
(5, 2, 2, '2023-02-02 10:00:00', 1),
(6, 2, 3, '2023-02-05 12:30:00', 1);

-- Project 3: E-Commerce Platform
INSERT INTO ProjectCollaborators (UserID, ProjectID, RoleID, JoinedAt, isActive) VALUES
(7, 3, 1, '2023-03-01 10:00:00', 1),
(8, 3, 2, '2023-03-02 11:00:00', 1),
(9, 3, 3, '2023-03-04 14:00:00', 1),
(10, 3, 4, '2023-03-06 15:00:00', 1);

-- Project 4: Blog Platform
INSERT INTO ProjectCollaborators (UserID, ProjectID, RoleID, JoinedAt, isActive) VALUES
(11, 4, 1, '2023-04-01 12:00:00', 1),
(12, 4, 2, '2023-04-02 13:00:00', 1),
(13, 4, 3, '2023-04-04 14:00:00', 1),
(14, 4, 4, '2023-04-06 15:00:00', 0); -- Inactive user

-- Project 5: AI Chatbot
INSERT INTO ProjectCollaborators (UserID, ProjectID, RoleID, JoinedAt, isActive) VALUES
(15, 5, 1, '2023-05-01 09:00:00', 1),
(16, 5, 2, '2023-05-02 11:30:00', 1),
(17, 5, 3, '2023-05-05 14:00:00', 1);

-- Project 6: Fitness App
INSERT INTO ProjectCollaborators (UserID, ProjectID, RoleID, JoinedAt, isActive) VALUES
(18, 6, 1, '2023-06-01 09:30:00', 1),
(19, 6, 2, '2023-06-02 10:30:00', 1),
(20, 6, 3, '2023-06-03 11:00:00', 1);

-- Project 7: Social Media Platform
INSERT INTO ProjectCollaborators (UserID, ProjectID, RoleID, JoinedAt, isActive) VALUES
(21, 7, 1, '2023-07-01 09:00:00', 1),
(22, 7, 2, '2023-07-02 10:30:00', 1),
(23, 7, 3, '2023-07-03 11:30:00', 1);

-- Project 8: Task Management Tool
INSERT INTO ProjectCollaborators (UserID, ProjectID, RoleID, JoinedAt, isActive) VALUES
(24, 8, 1, '2023-08-01 10:30:00', 1),
(25, 8, 2, '2023-08-02 11:30:00', 1),
(26, 8, 3, '2023-08-03 12:00:00', 1),
(27, 8, 4, '2023-08-04 12:30:00', 0); -- Inactive user

-- Project 9: Food Delivery App
INSERT INTO ProjectCollaborators (UserID, ProjectID, RoleID, JoinedAt, isActive) VALUES
(28, 9, 1, '2023-09-01 13:00:00', 1),
(29, 9, 2, '2023-09-02 14:30:00', 1),
(30, 9, 3, '2023-09-03 15:00:00', 1);

-- Project 10: Real Estate Platform
INSERT INTO ProjectCollaborators (UserID, ProjectID, RoleID, JoinedAt, isActive) VALUES
(31, 10, 1, '2023-10-01 10:00:00', 1),
(32, 10, 2, '2023-10-02 11:00:00', 1),
(33, 10, 3, '2023-10-03 12:00:00', 0); -- Inactive user

-- Project 11: Online Learning Platform
INSERT INTO ProjectCollaborators (UserID, ProjectID, RoleID, JoinedAt, isActive) VALUES
(34, 11, 1, '2023-11-01 09:00:00', 1),
(35, 11, 2, '2023-11-02 10:00:00', 1),
(36, 11, 3, '2023-11-03 11:00:00', 1),
(37, 11, 4, '2023-11-04 12:00:00', 1);

-- TaskCollaborators
-- Task 1: Website Redesign - Design homepage
INSERT INTO TaskCollaborators (UserID, RoleID, TaskID, JoinedAt, isActive) VALUES
(1, 2, 1, '2023-01-15 10:00:00', 1),
(2, 4, 1, '2023-01-20 11:00:00', 1),
(3, 4, 1, '2023-01-25 09:30:00', 1),
(4, 4, 1, '2023-01-28 12:00:00', 1);

-- Task 2: Mobile App Development - App login screen
INSERT INTO TaskCollaborators (UserID, RoleID, TaskID, JoinedAt, isActive) VALUES
(3, 2, 2, '2023-02-01 09:00:00', 1),
(4, 4, 2, '2023-02-10 10:30:00', 1),
(5, 4, 2, '2023-02-15 11:15:00', 1);

-- Task 3: E-Commerce Platform - Product page layout
INSERT INTO TaskCollaborators (UserID, RoleID, TaskID, JoinedAt, isActive) VALUES
(5, 4, 3, '2023-03-05 09:00:00', 1),
(6, 4, 3, '2023-03-10 10:30:00', 1),
(7, 4, 3, '2023-03-15 12:00:00', 1);

-- Task 4: Blog Platform - User profile design
INSERT INTO TaskCollaborators (UserID, RoleID, TaskID, JoinedAt, isActive) VALUES
(6, 4, 4, '2023-04-05 10:00:00', 1),
(7, 4, 4, '2023-04-07 11:30:00', 1),
(8, 4, 4, '2023-04-10 12:30:00', 1);

-- Task 5: AI Chatbot - AI model training
INSERT INTO TaskCollaborators (UserID, RoleID, TaskID, JoinedAt, isActive) VALUES
(9, 4, 5, '2023-05-01 13:00:00', 1),
(10, 4, 5, '2023-05-03 10:00:00', 1),
(11, 4, 5, '2023-05-10 09:30:00', 1);

-- Task 6: Fitness App - Workout logging feature
INSERT INTO TaskCollaborators (UserID, RoleID, TaskID, JoinedAt, isActive) VALUES
(12, 4, 6, '2023-06-01 10:00:00', 1),
(13, 4, 6, '2023-06-05 11:30:00', 1),
(14, 4, 6, '2023-06-10 13:00:00', 1);

-- Task 7: Social Media Platform - Create user feed
INSERT INTO TaskCollaborators (UserID, RoleID, TaskID, JoinedAt, isActive) VALUES
(15, 4, 7, '2023-06-15 12:00:00', 1),
(16, 4, 7, '2023-06-18 13:30:00', 1),
(17, 4, 7, '2023-06-20 11:00:00', 1);

-- Task 8: Task Management Tool - Task filtering
INSERT INTO TaskCollaborators (UserID, RoleID, TaskID, JoinedAt, isActive) VALUES
(18, 4, 8, '2023-07-01 10:00:00', 1),
(19, 4, 8, '2023-07-05 09:00:00', 1),
(20, 4, 8, '2023-07-07 12:00:00', 1);

-- Task 9: Food Delivery App - Restaurant profile page
INSERT INTO TaskCollaborators (UserID, RoleID, TaskID, JoinedAt, isActive) VALUES
(21, 4, 9, '2023-07-15 13:00:00', 1),
(22, 4, 9, '2023-07-20 14:30:00', 1),
(23, 4, 9, '2023-07-25 10:00:00', 1);

-- Task 10: Real Estate Platform - Property search functionality
INSERT INTO TaskCollaborators (UserID, RoleID, TaskID, JoinedAt, isActive) VALUES
(24, 4, 10, '2023-08-01 12:00:00', 1),
(25, 4, 10, '2023-08-05 13:30:00', 1),
(26, 4, 10, '2023-08-07 14:00:00', 1);

-- Task 11: Online Learning Platform - Course listing page
INSERT INTO TaskCollaborators (UserID, RoleID, TaskID, JoinedAt, isActive) VALUES
(27, 4, 11, '2023-08-10 10:00:00', 1),
(28, 4, 11, '2023-08-15 12:30:00', 1),
(29, 4, 11, '2023-08-18 13:00:00', 1);

-- Task 12: Website Redesign - Footer design
INSERT INTO TaskCollaborators (UserID, RoleID, TaskID, JoinedAt, isActive) VALUES
(1, 2, 12, '2023-01-20 12:00:00', 1),
(2, 4, 12, '2023-01-23 11:00:00', 1);

-- Task 13: Mobile App Development - Splash screen design
INSERT INTO TaskCollaborators (UserID, RoleID, TaskID, JoinedAt, isActive) VALUES
(3, 2, 13, '2023-02-03 10:00:00', 1),
(4, 4, 13, '2023-02-07 12:00:00', 1);

-- Task 14: E-Commerce Platform - Payment gateway integration
INSERT INTO TaskCollaborators (UserID, RoleID, TaskID, JoinedAt, isActive) VALUES
(5, 4, 14, '2023-03-12 09:00:00', 1),
(6, 4, 14, '2023-03-15 10:30:00', 1),
(7, 4, 14, '2023-03-17 11:00:00', 1);

-- Task 15: Blog Platform - Content creation feature
INSERT INTO TaskCollaborators (UserID, RoleID, TaskID, JoinedAt, isActive) VALUES
(8, 4, 15, '2023-04-12 10:00:00', 1),
(9, 4, 15, '2023-04-15 11:30:00', 1),
(10, 4, 15, '2023-04-17 12:30:00', 1);

-- Task 16: AI Chatbot - Integrate speech recognition
INSERT INTO TaskCollaborators (UserID, RoleID, TaskID, JoinedAt, isActive) VALUES
(11, 4, 16, '2023-06-02 14:00:00', 1),
(12, 4, 16, '2023-06-07 13:00:00', 1),
(13, 4, 16, '2023-06-10 11:00:00', 1);

-- Task 17: Fitness App - Fitness goals page
INSERT INTO TaskCollaborators (UserID, RoleID, TaskID, JoinedAt, isActive) VALUES
(14, 4, 17, '2023-07-01 10:30:00', 1),
(15, 4, 17, '2023-07-05 12:00:00', 1),
(16, 4, 17, '2023-07-10 14:00:00', 1);

-- Task 18: Social Media Platform - Post creation feature
INSERT INTO TaskCollaborators (UserID, RoleID, TaskID, JoinedAt, isActive) VALUES
(17, 4, 18, '2023-07-15 10:00:00', 1),
(18, 4, 18, '2023-07-20 12:30:00', 1),
(19, 4, 18, '2023-07-25 14:00:00', 1);

-- Task 19: Task Management Tool - Task notifications
INSERT INTO TaskCollaborators (UserID, RoleID, TaskID, JoinedAt, isActive) VALUES
(20, 4, 19, '2023-07-30 10:30:00', 1),
(21, 4, 19, '2023-08-02 11:00:00', 1),
(22, 4, 19, '2023-08-05 13:30:00', 1);

-- Task 20: Food Delivery App - Food delivery feature
INSERT INTO TaskCollaborators (UserID, RoleID, TaskID, JoinedAt, isActive) VALUES
(23, 4, 20, '2023-08-10 10:00:00', 1),
(24, 4, 20, '2023-08-12 11:30:00', 1),
(25, 4, 20, '2023-08-15 13:00:00', 1);

-- Task 21: Real Estate Platform - Agent dashboard
INSERT INTO TaskCollaborators (UserID, RoleID, TaskID, JoinedAt, isActive) VALUES
(26, 4, 21, '2023-08-18 14:00:00', 1),
(27, 4, 21, '2023-08-22 12:00:00', 1),
(28, 4, 21, '2023-08-25 13:30:00', 1);

-- Task 22: Online Learning Platform - Admin panel design
INSERT INTO TaskCollaborators (UserID, RoleID, TaskID, JoinedAt, isActive) VALUES
(29, 4, 22, '2023-08-28 10:00:00', 1),
(30, 4, 22, '2023-09-01 11:30:00', 1),
(31, 4, 22, '2023-09-03 13:00:00', 1);