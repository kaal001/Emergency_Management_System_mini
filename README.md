# Emergency Management System

## Complete Project Documentation, Teacher-Criteria Mapping, Class/Method Responsibility, GitHub, IntelliJ, Build, JAR/EXE and Team Contribution Guide

**Team:** ArrayOf5  
**Language:** Java 26  
**GUI:** Java Swing  
**Persistence:** Java Serialization  
**Database:** None  
**Application type:** Local desktop application  
**Current role model:** Admin-only  
**Architecture:** Model + Manager + UI + Persistence

---

# 1. Project Overview

Emergency Management System is a Java Swing desktop application for administrative management of emergency incidents, response teams, team assignments, history and reports.

The current version is intentionally **admin-only**. An older project version had a separate user workflow, but that workflow is not part of the current build.

Main application flow:

```text
app.Main
   |
   v
LoginFrame
   |
   v
AuthenticationManager
   |
   v
MainFrame
   |
   +-------------------+------------------+
   |                   |                  |
 Dashboard         Management          History
                       |
                       v
                 JTabbedPane
                 /     |      \
                /      |       \
      Emergencies    Teams    Assignments
                       |
                       v
                    Reports
```

The central design rule is:

```text
UI collects input
      ->
Manager applies business rules
      ->
Model stores state
      ->
FileManager persists state
```

---

# 2. Why This Architecture Was Chosen

The project follows separation of concerns so that each layer has a clear job.

### Model
Stores data only.

Examples:

```text
Admin
Emergency
ResponseTeam
Assignment
```

### Manager
Contains business logic.

Examples:

```text
CRUD
search
validation rules
team suitability
assignment rules
status transitions
statistics
```

### UI
Handles:

```text
JFrame
JPanel
JTable
forms
buttons
navigation
dialogs
messages
```

### Persistence
`FileManager` handles Java Serialization and `.dat` files.

### App
`Main` starts the application.

This structure also makes viva explanation easier because a feature can be traced from:

```text
button -> UI -> Manager -> Model -> FileManager
```

---

# 3. Current Feature Set

The current application provides:

- Administrator login
- Dashboard
- Emergency CRUD
- Response Team CRUD
- Assignment CRUD
- Search
- History and filters
- Full emergency details dialog
- Reports/statistics
- Critical emergency automatic team assignment
- Team availability management
- Emergency status state machine
- Input validation
- Delete confirmation
- Java Serialization persistence
- Responsive single-window navigation
- JMenuBar
- JTabbedPane
- JTable
- JDialog

---

# 4. Source Tree

```text
EmergencyManagementSystem/
|
+-- src/
|   |
|   +-- app/
|   |   +-- Main.java
|   |
|   +-- enums/
|   |   +-- EmergencyType.java
|   |   +-- Priority.java
|   |   +-- EmergencyStatus.java
|   |   +-- TeamType.java
|   |
|   +-- manager/
|   |   +-- EmergencyManager.java
|   |   +-- TeamManager.java
|   |   +-- AssignmentManager.java
|   |   +-- AuthenticationManager.java
|   |
|   +-- model/
|   |   +-- Admin.java
|   |   +-- Emergency.java
|   |   +-- ResponseTeam.java
|   |   +-- AmbulanceTeam.java
|   |   +-- FireTeam.java
|   |   +-- RescueTeam.java
|   |   +-- SecurityTeam.java
|   |   +-- Assignment.java
|   |
|   +-- persistence/
|   |   +-- FileManager.java
|   |
|   +-- ui/
|       +-- Theme.java
|       +-- LoginFrame.java
|       +-- MainFrame.java
|       +-- DashboardPanel.java
|       +-- EmergencyManagementPanel.java
|       +-- TeamManagementPanel.java
|       +-- AssignmentManagementPanel.java
|       +-- ManagementTabbedPanel.java
|       +-- EmergencyHistoryPanel.java
|       +-- ReportPanel.java
|       +-- EmergencyDetailsDialog.java
|
+-- data/                 # runtime-created
+-- README.md
+-- README.txt
+-- .gitignore
```

Runtime persistence files are normally:

```text
data/admins.dat
data/emergencies.dat
data/teams.dat
data/assignments.dat
```

The active user-facing management workflow persists emergencies, teams and assignments. The persistence utility also contains administrator save/load support, while the current login flow uses the default administrator created by `AuthenticationManager`.

---

# 5. Enum Classes

## `EmergencyType.java`

Values:

```text
MEDICAL
FIRE
ROAD_ACCIDENT
SECURITY
NATURAL_DISASTER
GAS_LEAK
ELECTRICAL_EMERGENCY
BUILDING_COLLAPSE
INDUSTRIAL_ACCIDENT
MISSING_PERSON
WATER_FLOOD_EMERGENCY
```

Used by emergency forms, managers, history filters and reports.

## `Priority.java`

```text
CRITICAL
HIGH
MEDIUM
LOW
```

Used by emergency creation, updates, critical auto-assignment, history and reports.

## `EmergencyStatus.java`

```text
PENDING
ASSIGNED
IN_PROGRESS
RESOLVED
CANCELLED
```

Controls the emergency lifecycle.

## `TeamType.java`

```text
AMBULANCE
FIRE
RESCUE
SECURITY
```

Connects emergency categories to response-team categories.

---

# 6. Model Layer

## `Admin.java`

Stores:

```text
adminId
name
username
password
email
contactNumber
```

Implements `Serializable`.

Contains:

```text
default constructor
parameterized constructor
getters
setters
toString()
```

Teacher criteria demonstrated:

```text
Encapsulation
Constructors
private/public
this
Serializable
```

---

## `Emergency.java`

Stores:

```text
emergencyId
EmergencyType type
Priority priority
location
description
dateTime
EmergencyStatus status
assignedTeamId
```

Default state:

```text
status = PENDING
assignedTeamId = null
```

Contains default and parameterized constructors plus getters/setters.

The UI generates the user-visible ID, while the manager also supports generation when the ID is blank.

---

## `ResponseTeam.java`

The most important OOP parent class.

```java
public abstract class ResponseTeam implements Serializable
```

Fields:

```text
teamId
teamName
TeamType teamType
contactNumber
memberCount
available
```

Important methods/concepts:

```text
constructors
getters/setters
protected getTeamResponseMessage()
abstract respondToEmergency()
toString()
```

Teacher mapping:

```text
Abstraction -> abstract class + abstract method
Encapsulation -> private fields + getters/setters
Protected -> getTeamResponseMessage()
```

---

# 7. Inheritance and Polymorphism

The hierarchy is:

```text
ResponseTeam
├── AmbulanceTeam
├── FireTeam
├── RescueTeam
└── SecurityTeam
```

Each child:

- extends `ResponseTeam`
- uses `super(...)` in its parameterized constructor
- sets its own `TeamType`
- overrides `respondToEmergency()`

This demonstrates:

```text
Inheritance
Method overriding
Runtime polymorphism
super
Abstraction
```

`ArrayList<ResponseTeam>` can hold different concrete objects:

```text
AmbulanceTeam
FireTeam
RescueTeam
SecurityTeam
```

That is the project's clearest polymorphism example.

---

# 8. `Assignment.java`

Stores the relationship between an emergency and a response team.

Fields:

```text
assignmentId
emergencyId
teamId
assignedTime
notes
```

Contains constructors, getters, setters and `toString()`.

Conceptual relationship:

```text
Emergency <---- Assignment ----> ResponseTeam
```

---

# 9. Manager Layer

The manager layer is where the main business rules live.

```text
EmergencyManager
TeamManager
AssignmentManager
AuthenticationManager
```

The UI does not directly decide every business rule.

Example:

```text
Delete Button
   |
   v
JOptionPane confirmation
   |
   v
Manager removal rule
   |
   v
Model update
   |
   v
FileManager save
```

---

# 10. `EmergencyManager.java`

Main responsibilities:

```text
Emergency ArrayList
Emergency CRUD
ID generation
search/filter
priority updates
status transitions
statistics
save/load integration
```

Important functionality includes:

- `addEmergency(...)`
- `updateEmergency(...)`
- `removeEmergency(...)`
- `findEmergencyById(...)`
- ID generation by emergency category
- emergency search/filter methods
- priority update logic
- status update/state machine logic
- count/statistics methods
- save/load integration

## Emergency ID algorithm

Current category prefixes:

```text
MEDICAL               -> MED
FIRE                  -> FIR
ROAD_ACCIDENT         -> ACC
SECURITY              -> SEC
NATURAL_DISASTER      -> NAT
GAS_LEAK              -> GAS
ELECTRICAL_EMERGENCY  -> ELE
BUILDING_COLLAPSE     -> BLD
INDUSTRIAL_ACCIDENT   -> IND
MISSING_PERSON        -> MIS
WATER_FLOOD_EMERGENCY -> FLD
```

Example:

```text
MED-001
MED-002
FIR-001
ACC-001
```

The generator examines existing IDs and uses the highest numeric suffix for the selected prefix, then increments it.

---

# 11. Emergency Manager — CRUD and Search

### Add

Validates the object, generates an ID if required, prevents duplicate IDs, adds to `ArrayList<Emergency>`, and saves.

### Update

Updates allowed emergency fields while retaining identity/workflow fields that should not be casually overwritten.

### Delete

Applies workflow restrictions before removing an emergency and saves afterward.

### Find

`findEmergencyById(...)` performs a linear search by case-insensitive ID.

### Search

Search/filter logic uses straightforward string operations such as:

```text
trim()
toLowerCase()
contains()
equalsIgnoreCase()
```

Typical searched information:

```text
ID
location
description
type
priority
status
```

Complexity of a simple linear pass:

```text
O(n)
```

---

# 12. Emergency State Machine

Valid workflow:

```text
PENDING
   |
   +--> ASSIGNED
   |       |
   |       +--> IN_PROGRESS
   |                |
   |                +--> RESOLVED
   |
   +--> CANCELLED
```

Also allowed where applicable:

```text
ASSIGNED -> CANCELLED
IN_PROGRESS -> CANCELLED
```

Terminal states:

```text
RESOLVED
CANCELLED
```

This prevents invalid jumps such as:

```text
PENDING -> RESOLVED
RESOLVED -> IN_PROGRESS
```

Teacher/viva point:

> The state machine keeps emergency status changes controlled instead of allowing arbitrary values from the UI.

---

# 13. Emergency Statistics

Manager statistics support Dashboard and Reports.

Main values:

```text
Total emergencies
Pending
Critical
In Progress
Resolved
Unassigned
Available teams
Busy teams
```

Examples of the underlying logic:

```text
Total = emergencies.size()

Pending = count status PENDING
Critical = count priority CRITICAL
In Progress = count status IN_PROGRESS
Resolved = count status RESOLVED
Unassigned = count assignedTeamId == null
Available Teams = count available == true
Busy Teams = count available == false
```

---

# 14. `TeamManager.java`

Main responsibilities:

```text
Team ArrayList
Team CRUD
Team search
availability filtering
team type filtering
suitable-team detection
default team creation
persistence
```

Important behavior:

```text
addTeam(...)
updateTeam(...)
removeTeam(...)
findTeamById(...)
getAllTeams()
getAvailableTeams()
getBusyTeams()
getTeamsByType(...)
findSuitableTeams(...)
```

---

# 15. Default Response Teams

The project can create default demonstration teams when saved team data is unavailable.

```text
AT-001  Central Ambulance Team  01711111111  Members=4
FT-001  Central Fire Team       01722222222  Members=6
RT-001  Central Rescue Team     01733333333  Members=5
ST-001  Central Security Team   01744444444  Members=8
```

All start as available.

---

# 16. Team Suitability Logic

Emergency -> required response type:

```text
MEDICAL                 -> AMBULANCE
FIRE                    -> FIRE
GAS_LEAK                -> FIRE
ROAD_ACCIDENT           -> RESCUE
ELECTRICAL_EMERGENCY    -> RESCUE
BUILDING_COLLAPSE       -> RESCUE
INDUSTRIAL_ACCIDENT     -> RESCUE
NATURAL_DISASTER        -> RESCUE
WATER_FLOOD_EMERGENCY   -> RESCUE
SECURITY                -> SECURITY
MISSING_PERSON         -> SECURITY
```

Suitability answers:

```text
"Is this the correct kind of team?"
```

Availability answers:

```text
"Is that team currently free?"
```

The assignment layer checks both.

---

# 17. `AssignmentManager.java`

This manager coordinates three entities:

```text
Emergency
ResponseTeam
Assignment
```

Main functionality:

```text
addAssignment(...)
assignTeam(...)
updateAssignment(...)
removeAssignment(...)
find/search assignment
assignment ID generation
save all affected data
```

## Assignment creation checks

Before assignment:

```text
Emergency exists
Team exists
Emergency is PENDING
Team is available
Team is suitable
Assigned time is valid
```

Then:

```text
generate AS-xxxx
       ->
create Assignment
       ->
add assignment
       ->
emergency.assignedTeamId = teamId
       ->
emergency.status = ASSIGNED
       ->
team.available = false
       ->
save assignments + emergencies + teams
```

This is an important multi-object business operation.

---

# 18. Assignment ID Generation

Examples:

```text
AS-1001
AS-1002
AS-1003
```

The generator checks existing IDs and selects the highest numeric suffix, then adds one.

This is safer than simply using `assignments.size()+1001` because deletions can create gaps.

---

# 19. `AuthenticationManager.java`

Handles administrator authentication.

Default account:

```text
Admin ID : A001
Name     : System Administrator
Username : admin
Password : admin123
Email    : admin@ems.com
Contact  : 01700000000
```

Main operations:

```text
authenticate(...)
addAdmin(...)
findAdminByUsername(...)
getAllAdmins()
setAdmins(...)
```

The current application has no Admin CRUD screen. Authentication uses the manager's default administrator.

---

# 20. `FileManager.java`

`FileManager` is the persistence utility.

Core idea:

```text
Manager decides WHEN to save.
FileManager decides HOW to save.
```

Serialization uses:

```text
ObjectOutputStream
ObjectInputStream
```

Supported serialized collections include:

```text
Admins
Emergencies
Teams
Assignments
```

Runtime files:

```text
data/admins.dat
data/emergencies.dat
data/teams.dat
data/assignments.dat
```

The active application workflow heavily uses:

```text
emergencies.dat
teams.dat
assignments.dat
```

---

# 21. `Main.java`

Main responsibilities:

```text
start Swing Event Dispatch Thread
create AuthenticationManager
create LoginFrame
show login
```

The entry class is intentionally small.

The fully-qualified main class is:

```text
app.Main
```

---

# 22. `LoginFrame.java`

A compact login window.

Uses:

```text
JFrame
JTextField
JPasswordField
JButton
JLabel
JOptionPane
```

Flow:

```text
Username + Password
        |
        v
AuthenticationManager.authenticate(...)
        |
     +--+--+
     |     |
   success failure
     |     |
     v     v
MainFrame  warning/error
```

---

# 23. `MainFrame.java`

Main application shell.

Creates the shared objects:

```text
FileManager
EmergencyManager
TeamManager
AssignmentManager
```

Then shares them across panels.

Main pages:

```text
Dashboard
Management
History
Reports
```

Main responsibilities:

```text
buildUI()
createSidebar()
createHeader()
showPage(...)
setActiveButton(...)
refreshCurrentPage()
logout()
confirmExit()
createMenuBar()
showAboutDialog()
```

The main UI is intentionally one JFrame.

---

# 24. MainFrame Layout

```text
+----------------------+------------------------------------------+
|                      |                 HEADER                   |
|                      +------------------------------------------+
|      SIDEBAR         |                                          |
|                      |                CONTENT                   |
|  DASHBOARD           |                                          |
|  MANAGEMENT          |                                          |
|  HISTORY             |                                          |
|  REPORTS             |                                          |
|                      |                                          |
|                      |                                          |
|      LOGOUT          |                                          |
+----------------------+------------------------------------------+
```

The sidebar remains at the left; the right content area is responsive.

`CardLayout` switches between the pages.

---

# 25. `DashboardPanel.java`

Shows:

```text
Total Emergencies
Pending
Critical
In Progress
Resolved
Unassigned
Available Teams
Busy Teams
```

Also displays recent emergencies in a JTable.

Main refresh operation:

```text
refreshDashboard()
```

The panel reads current manager data instead of maintaining a second copy.

---

# 26. `ManagementTabbedPanel.java`

Uses `JTabbedPane`.

Current entity tabs:

```text
Emergencies
Response Teams
Assignments
```

This directly satisfies the teacher's one-tab-per-entity approach for the managed business entities.

The class provides:

```text
refreshSelectedTab()
refreshAllTabs()
```

---

# 27. `EmergencyManagementPanel.java`

Complete emergency management screen.

Main controls:

```text
Emergency ID (read-only/automatic)
Emergency Type
Priority
Location
Description
Date/Time (automatic)
```

Buttons:

```text
ADD
UPDATE
DELETE
SEARCH
CLEAR
```

Uses:

```text
JTextField
JTextArea
JComboBox
JTable
JScrollPane
JOptionPane
```

Important operations include:

```text
ID generation
input validation
CRUD
search
critical auto-assignment
row selection
form population
```

---

# 28. Critical Emergency Automatic Assignment

This feature is implemented at the emergency-management workflow level.

Logic:

```text
Create emergency
       |
       v
Priority == CRITICAL ?
       |
      YES
       |
       v
Find suitable available response team
       |
     +--+--+
     |     |
   found  none
     |     |
     v     v
assign   remain PENDING
     |
     v
status = ASSIGNED
team = BUSY
```

This feature connects:

```text
EmergencyManagementPanel
TeamManager
AssignmentManager
Emergency
ResponseTeam
Assignment
```

---

# 29. `TeamManagementPanel.java`

Complete team CRUD screen.

Form:

```text
Team ID
Team Name
Team Type
Contact
Members
Available
```

Swing components:

```text
JTextField
JComboBox
JSpinner
JCheckBox
JButton
JTable
JScrollPane
JOptionPane
```

The selected `TeamType` determines the concrete subclass created by the UI.

Example:

```text
AMBULANCE -> AmbulanceTeam
FIRE      -> FireTeam
RESCUE    -> RescueTeam
SECURITY  -> SecurityTeam
```

---

# 30. `AssignmentManagementPanel.java`

Complete assignment management screen.

Fields:

```text
Assignment ID
Emergency
Response Team
Assigned Time
Notes
```

Buttons:

```text
ASSIGN
UPDATE
DELETE
SEARCH
CLEAR
```

The manager checks team suitability and availability rather than allowing an arbitrary emergency/team pair.

---

# 31. `EmergencyHistoryPanel.java`

Read-oriented history screen.

Filters:

```text
Search keyword
Type
Priority
Status
```

Actions:

```text
FILTER
CLEAR
REFRESH
```

Main public refresh method:

```text
loadHistory()
```

Double-clicking a row opens:

```text
EmergencyDetailsDialog
```

---

# 32. `EmergencyDetailsDialog.java`

Extends:

```java
JDialog
```

Shows:

```text
Emergency ID
Type
Priority
Status
Location
Assigned Team
Date / Time
Record Type
Description
```

This is the project's dedicated `JDialog` implementation and avoids opening a separate main JFrame just to inspect one record.

---

# 33. `ReportPanel.java`

Shows summary cards:

```text
Total Emergencies
Critical
Pending
Assigned
In Progress
Resolved
Available Teams
Busy Teams
```

Also provides report tables for:

```text
Emergency Type Summary
Priority Summary
Status Summary
Response Team Summary
```

Main method:

```text
refreshReports()
```

---

# 34. `Theme.java`

Central theme:

```text
BLACK        #0A0908
JET_BLACK    #22333B
ALMOND_CREAM #EAE0D5
KHAKI_BEIGE  #C6AC8F
STONE_BROWN  #5E503F
```

Reusable styling methods include:

```text
styleAdminMenuButton(...)
styleLogoutButton(...)
styleHeaderButton(...)
stylePrimaryButton(...)
styleSecondaryButton(...)
styleDangerButton(...)
```

This keeps the application visually consistent.

---

# 35. Teacher Requirement Mapping — OOP

| Teacher Criterion | Where Used | What to Explain |
|---|---|---|
| Encapsulation | All model classes | Private fields + getters/setters |
| Inheritance | `AmbulanceTeam`, `FireTeam`, `RescueTeam`, `SecurityTeam` | `extends ResponseTeam` |
| Polymorphism | Response team hierarchy | Overridden `respondToEmergency()` and `ResponseTeam` references |
| Abstraction | `ResponseTeam` | Abstract class + abstract method |
| `this` | Model constructors/setters | Current object reference |
| `super` | Team subclass constructors | Calls parent constructor |
| `static` | Constants / serialization identifiers | Class-level values |
| `final` | MainFrame manager references | Prevents reference replacement |
| Constructors | Model classes | Default + parameterized constructors |
| `private` | Model fields | Encapsulation |
| `public` | Public classes/methods | Cross-package access |
| `protected` | `ResponseTeam.getTeamResponseMessage()` | Parent-to-child access |
| ArrayList | Manager classes | Dynamic in-memory collections |
| String formatting | UI + manager search/ID logic | `trim`, `contains`, case conversion, formatting |

---

# 36. Teacher Requirement Mapping — Swing

| Requirement | Class | Exact Use |
|---|---|---|
| `JFrame` | `LoginFrame`, `MainFrame` | Main windows |
| `JTabbedPane` | `ManagementTabbedPanel` | Emergency/Team/Assignment tabs |
| `JTable` | Management panels, History, Reports, Dashboard | CRUD/data display |
| Add | Management panels | Create records |
| Update | Management panels | Edit records |
| Delete | Management panels | Remove records |
| Search | Management panels + History | Keyword lookup/filter |
| `JOptionPane` | Login + management UIs | Validation/status/confirmation |
| Delete confirmation | Emergency/Team/Assignment UI | `showConfirmDialog(...)` |
| `JTextField` | Forms/search/login | Short text input |
| `JButton` | All UI screens | Actions |
| `JLabel` | All UI screens | Labels/headings/statistics |
| `JDialog` | `EmergencyDetailsDialog` | Full emergency details |
| `JComboBox` | Type/priority/status/team selection | Controlled input |
| `JCheckBox` | Team Management | Availability |
| `JSpinner` | Team Management | Member count |
| `JScrollPane` | Tables/text areas | Scroll support |
| `JSplitPane` | Management UIs | Responsive form/table split |
| `JMenuBar` | `MainFrame` | File/View/Help |
| `CardLayout` | `MainFrame` | One-window navigation |

---

# 37. CRUD Requirement Matrix

## Emergency entity

UI:

```text
EmergencyManagementPanel
```

Manager:

```text
EmergencyManager
```

Operations:

```text
Add
Update
Delete
Search
```

## Response Team entity

UI:

```text
TeamManagementPanel
```

Manager:

```text
TeamManager
```

Operations:

```text
Add
Update
Delete
Search
```

## Assignment entity

UI:

```text
AssignmentManagementPanel
```

Manager:

```text
AssignmentManager
```

Operations:

```text
Add
Update
Delete
Search
```

History and Reports are intentionally read-oriented screens rather than CRUD entity tabs.

---

# 38. Validation and Confirmation Matrix

```text
Login
  -> empty credential warning
  -> invalid credential error

Emergency
  -> type required
  -> priority required
  -> location required
  -> description required
  -> duplicate ID prevented

Team
  -> required values
  -> valid member count
  -> duplicate Team ID prevented

Assignment
  -> valid emergency
  -> valid team
  -> pending emergency
  -> available team
  -> suitable team
  -> valid assigned time

Delete
  -> confirmation dialog
  -> business rule check
```

---

# 39. Example Feature Trace: Add Emergency

```text
User clicks ADD
      |
      v
EmergencyManagementPanel
      |
      +--> validate fields
      |
      +--> generate ID
      |
      v
Emergency object
      |
      v
EmergencyManager.addEmergency(...)
      |
      +--> duplicate check
      +--> ArrayList.add(...)
      +--> save
      |
      v
FileManager
      |
      v
emergencies.dat
      |
      v
refresh JTable
```

---

# 40. Example Feature Trace: Critical Auto-Assignment

```text
Add emergency
      |
      v
priority = CRITICAL
      |
      v
find suitable available team
      |
      +------------------+
      |                  |
    found              none
      |                  |
      v                  v
assign team           remain PENDING
      |
      v
create Assignment
      |
      v
Emergency = ASSIGNED
      |
      v
Team = BUSY
      |
      v
save all affected data
```

---

# 41. Example Feature Trace: Assignment

```text
AssignmentManagementPanel
       |
       v
AssignmentManager.assignTeam(...)
       |
       +--> find Emergency
       +--> find Team
       +--> check PENDING
       +--> check AVAILABLE
       +--> check SUITABLE
       |
       v
Assignment object
       |
       +--> emergency.assignedTeamId
       +--> emergency.status = ASSIGNED
       +--> team.available = false
       |
       v
FileManager
```

---

# 42. Example Feature Trace: History Details

```text
History JTable
      |
      v
Double-click row
      |
      v
Read Emergency ID
      |
      v
EmergencyManager.findEmergencyById(...)
      |
      v
EmergencyDetailsDialog
      |
      v
Full record displayed
```

---

# 43. Example Feature Trace: Reports

```text
ReportPanel.refreshReports()
        |
        +--> EmergencyManager data
        +--> TeamManager data
        |
        v
count/group records
        |
        v
update labels + report tables
```

---

# 44. Java Concepts in One Place

The teacher can ask where each concept appears:

### Encapsulation

```java
private String location;
```

with getters/setters.

### Inheritance

```java
class FireTeam extends ResponseTeam
```

### Polymorphism

```java
ResponseTeam team = new FireTeam(...);
```

and overridden:

```text
respondToEmergency()
```

### Abstraction

```java
abstract class ResponseTeam
```

### `this`

```java
this.teamName = teamName;
```

### `super`

```java
super(...);
```

### `static`

Used by class-level constants/serialization identifiers.

### `final`

Used for shared manager references/constants that should not be reassigned.

---

# 45. Search Algorithms

Search is intentionally simple.

For each record:

```text
normalize text
      |
      v
check keyword
      |
      +--> matches ID?
      +--> matches location?
      +--> matches description?
      +--> matches type?
      +--> matches priority/status?
      |
      v
keep matching record
```

This is linear search and is easy to explain for a small lab dataset.

---

# 46. GitHub Repository Contents

Recommended repository:

```text
EmergencyManagementSystem
```

Repository should contain source/documentation, not IDE cache/build output.

Recommended tracked files:

```text
src/
README.md
README.txt
.gitignore
```

Recommended ignored files:

```text
.idea/
*.iml
out/
build/
dist/
installer/
package/
sources.txt
data/*.dat
```

---

# 47. `.gitignore`

Recommended file:

```gitignore
# IntelliJ
.idea/
*.iml

# Compiled output
out/
classes/

# Build/package output
build/
dist/
installer/
package/

# Runtime application data
data/*.dat

# Temporary compiler source list
sources.txt

# OS files
.DS_Store
Thumbs.db
```

Do not ignore source code or README files.

---

# 48. Publish the Project to GitHub — IntelliJ Method

1. Create a GitHub repository.
2. Suggested name:

```text
EmergencyManagementSystem
```

3. In IntelliJ:

```text
VCS
 -> Enable Version Control Integration
 -> Git
```

4. Add files:

```text
Git -> Add
```

5. Commit:

```text
Initial Emergency Management System
```

6. Add the GitHub remote.

Example:

```text
https://github.com/<USERNAME>/EmergencyManagementSystem.git
```

7. Push:

```text
Git -> Push
```

8. Open GitHub and verify `src`, `README.md`, `README.txt` and `.gitignore` are visible.

---

# 49. Publish the Project to GitHub — Command Line

From the project root:

```powershell
git --version
git init
git add .
git status
git commit -m "Initial Emergency Management System"
git branch -M main
git remote add origin https://github.com/<USERNAME>/EmergencyManagementSystem.git
git push -u origin main
```

For future changes:

```powershell
git add .
git commit -m "Update emergency management features"
git push
```

Useful commit names:

```text
Add emergency history module
Add report statistics
Improve team assignment logic
Fix dashboard refresh
Add emergency details dialog
Improve UI validation
Update documentation
```

---

# 50. How a Person Downloads from GitHub

## ZIP method

```text
Open repository
   -> Code
   -> Download ZIP
   -> Extract
   -> Open project in IntelliJ
```

## Git method

```powershell
git clone https://github.com/<USERNAME>/EmergencyManagementSystem.git
cd EmergencyManagementSystem
```

Then open the folder in IntelliJ.

---

# 51. IntelliJ Setup from Source

Required:

```text
IntelliJ IDEA
JDK 26
```

Steps:

1. `File -> Open`.
2. Select `EmergencyManagementSystem`.
3. Configure project SDK:

```text
File
 -> Project Structure
 -> Project
 -> SDK = JDK 26
```

4. Confirm `src` is a Sources Root. If not:

```text
Right Click src
 -> Mark Directory as
 -> Sources Root
```

5. Build:

```text
Build -> Build Project
```

6. Run:

```text
app.Main
```

Recommended working directory:

```text
Project root
```

This keeps relative `data/` persistence predictable.

---

# 52. Command-Line Compilation on Windows

Open PowerShell at the project root.

Check versions:

```powershell
java -version
javac -version
```

They should report JDK 26.

Create output:

```powershell
Remove-Item -Recurse -Force .\out -ErrorAction SilentlyContinue
New-Item -ItemType Directory .\out | Out-Null
```

Create source list:

```powershell
Get-ChildItem .\src -Recurse -Filter *.java |
    ForEach-Object { $_.FullName } |
    Set-Content .\sources.txt
```

Compile:

```powershell
javac -encoding UTF-8 -d .\out @.\sources.txt
```

Run:

```powershell
java -cp .\out app.Main
```

The project uses packages, so the main class is `app.Main`, not just `Main`.

---

# 53. Build a JAR

Create a distribution directory:

```powershell
New-Item -ItemType Directory .\dist -ErrorAction SilentlyContinue | Out-Null
```

Create the JAR:

```powershell
jar --create --file .\dist\EmergencyManagementSystem.jar --main-class app.Main -C .\out .
```

Run it:

```powershell
java -jar .\dist\EmergencyManagementSystem.jar
```

The JAR requires Java to already be installed on the target machine.

---

# 54. Build a Windows Application Image with `jpackage`

JDK 26 provides `jpackage` for creating self-contained application bundles.

First make sure the JAR is in `dist/`.

Create destination:

```powershell
New-Item -ItemType Directory .\package -ErrorAction SilentlyContinue | Out-Null
```

Run:

```powershell
jpackage `
  --type app-image `
  --name EmergencyManagementSystem `
  --input .\dist `
  --main-jar EmergencyManagementSystem.jar `
  --main-class app.Main `
  --dest .\package
```

The application image contains a bundled runtime and does not require the user to launch it from IntelliJ.

---

# 55. Build a Windows EXE Installer

For Windows installer packaging, `jpackage` supports `exe`.

Prerequisites:

```text
Windows
JDK 26
WiX Toolset 3.0 or later
compiled JAR
```

Command:

```powershell
New-Item -ItemType Directory .\installer -ErrorAction SilentlyContinue | Out-Null

jpackage `
  --type exe `
  --name EmergencyManagementSystem `
  --input .\dist `
  --main-jar EmergencyManagementSystem.jar `
  --main-class app.Main `
  --dest .\installer `
  --win-shortcut `
  --win-menu
```

The generated filename is version-dependent, but it will be a Windows `.exe` installer inside `installer/`.

---

# 56. EXE User Workflow

A normal Windows user does not need IntelliJ if using the packaged application.

```text
Download installer EXE
        |
        v
Run installer
        |
        v
Install application
        |
        v
Launch application
        |
        v
Login
```

Login:

```text
Username: admin
Password: admin123
```

---

# 57. Important EXE/Data Note

The application's `FileManager` uses a relative `data/` path.

Therefore the packaged application must have permission to create/write its runtime data.

For classroom/demo distribution, keep the packaged application in a writable location.

A future production implementation could move persistence to a user-specific application-data directory.

---

# 58. Team Information

**Team Name:** `ArrayOf5`

The same five-member team naming convention from the earlier project is retained.

Contribution allocation:

# 59. Takbir 

Primary area:

```text
Core Architecture + Integration + Persistence + Main Business Logic
```

Main responsibility/files:

```text
app/Main.java
ui/MainFrame.java
manager/EmergencyManager.java
manager/AssignmentManager.java
persistence/FileManager.java
```

Responsibilities:

- Overall architecture
- Shared manager design
- Main application integration
- Emergency business rules
- Status state machine
- Emergency ID generation
- Assignment/persistence integration
- MainFrame navigation
- Cross-module testing
- Final integration

Viva focus:

```text
application flow
Model/Manager/UI/Persistence separation
EmergencyManager
Assignment synchronization
FileManager
CardLayout
persistence
```

---

# 60. Shuvo 

Primary area:

```text
Emergency Management + History + Emergency Details
```

Main responsibility/files:

```text
model/Emergency.java
ui/EmergencyManagementPanel.java
ui/EmergencyHistoryPanel.java
ui/EmergencyDetailsDialog.java
enums/EmergencyType.java
enums/Priority.java
enums/EmergencyStatus.java
```

Responsibilities:

- Emergency form and JTable
- Add/update/delete/search
- Input validation
- Auto ID display
- Date/time handling
- Critical-emergency workflow UI
- History filters
- History refresh
- Double-click details
- JDialog implementation

Viva focus:

```text
Emergency CRUD
validation
critical auto-assignment trigger
search/filter
JTable
JDialog
```

---

# 61. Ifaz 

Primary area:

```text
Response Team Management + OOP Hierarchy
```

Main responsibility/files:

```text
model/ResponseTeam.java
model/AmbulanceTeam.java
model/FireTeam.java
model/RescueTeam.java
model/SecurityTeam.java
manager/TeamManager.java
ui/TeamManagementPanel.java
enums/TeamType.java
```

Responsibilities:

- Abstract parent class
- Child subclasses
- Constructors
- `super`
- Method overriding
- Polymorphism
- Team CRUD
- Availability
- Suitable-team logic
- Team UI

Viva focus:

```text
abstraction
inheritance
polymorphism
overriding
super
protected
team suitability
```

---

# 62. Ehsan 

Primary area:

```text
Assignment Management + Authentication
```

Main responsibility/files:

```text
model/Assignment.java
manager/AssignmentManager.java
manager/AuthenticationManager.java
model/Admin.java
ui/AssignmentManagementPanel.java
ui/LoginFrame.java
```

Responsibilities:

- Admin login
- Authentication
- Assignment model
- Assignment CRUD
- Assignment ID generation
- Team availability checks
- Emergency/team synchronization
- Assignment UI

Viva focus:

```text
login flow
AssignmentManager
assignment business rules
Emergency-Team relationship
```

---

# 63. Suraiya 

Primary area:

```text
Dashboard + Reports + Theme + UI QA + Documentation
```

Main responsibility/files:

```text
ui/DashboardPanel.java
ui/ReportPanel.java
ui/Theme.java
```

Responsibilities:

- Dashboard statistic cards
- Recent emergency table
- Report summary tables
- Refresh behavior
- Theme/color/font consistency
- UI testing
- Documentation support
- Presentation/demo support

Viva focus:

```text
Dashboard
statistics
reports
Swing layout
Theme
testing
```

---

# 64. Contribution Table

| Member | Contribution | Main Area |
|---|---:|---|
| Takbir | 30% | Architecture, core business logic, integration, persistence |
| Shuvo | 18% | Emergency, history, details dialog |
| Ifaz | 16% | Team hierarchy, team manager, team UI |
| Ehsan | 18% | Authentication and assignments |
| Suraiya | 18% | Dashboard, reports, theme, QA/docs |
| **Total** | **100%** | |

---

# 65. Viva Rule: WHAT -> WHERE -> HOW -> WHY

When a teacher asks about a feature, answer in this order:

```text
WHAT is the feature?
      |
      v
WHERE is it implemented?
      |
      v
HOW does the function work?
      |
      v
WHY was it designed that way?
```

Example:

> The critical emergency auto-assignment feature is implemented in the emergency management workflow. The UI checks whether the priority is CRITICAL, asks `TeamManager` for a suitable available team, and uses `AssignmentManager` to create the assignment and synchronize emergency/team state. This keeps team selection and assignment rules out of the UI code as much as possible.

---

# 66. Teacher Demo Checklist

```text
[ ] Login
[ ] Dashboard
[ ] Management tabs
[ ] Emergency Add
[ ] Emergency Update
[ ] Emergency Delete
[ ] Emergency Search
[ ] Team Add
[ ] Team Update
[ ] Team Delete
[ ] Team Search
[ ] Assignment Add
[ ] Assignment Update
[ ] Assignment Delete
[ ] Assignment Search
[ ] Validation warnings
[ ] Delete confirmation
[ ] Critical auto-assignment
[ ] Team becomes BUSY
[ ] History filters
[ ] JDialog details
[ ] Reports
[ ] Refresh
[ ] Restart and persistence
```

---

# 67. Recommended Final Presentation Flow

```text
1. Login
2. Dashboard
3. Management -> Emergencies
4. Add CRITICAL emergency
5. Show automatic ID
6. Show automatic team assignment
7. Open Response Teams
8. Show selected team BUSY
9. Open Assignments
10. Show assignment
11. Open History
12. Filter/search
13. Double-click for JDialog
14. Open Reports
15. Show updated statistics
16. Restart application
17. Show persisted data
```

---

# 68. Testing Checklist

## Login

```text
[ ] correct credentials succeed
[ ] wrong username fails
[ ] wrong password fails
[ ] empty fields warn
```

## Emergency

```text
[ ] Add
[ ] Update
[ ] Delete
[ ] Search
[ ] Validation
[ ] Duplicate ID prevention
[ ] Critical auto-assignment
```

## Teams

```text
[ ] Add
[ ] Update
[ ] Delete
[ ] Search
[ ] Member count
[ ] Availability
[ ] Concrete subclass creation
```

## Assignments

```text
[ ] Add
[ ] Update
[ ] Delete
[ ] Search
[ ] Suitable team check
[ ] Available team check
[ ] Emergency state update
[ ] Team availability update
```

## History/Reports

```text
[ ] Search
[ ] Type filter
[ ] Priority filter
[ ] Status filter
[ ] JDialog
[ ] Report counts
[ ] Refresh
```

## Persistence

```text
[ ] Add records
[ ] Close application
[ ] Reopen application
[ ] Records still exist
[ ] Team availability remains consistent
[ ] Assignment data remains consistent
```

---

# 69. Recommended GitHub Screenshots

Optional folder:

```text
screenshots/
```

Suggested files:

```text
01-login.png
02-dashboard.png
03-emergency-management.png
04-team-management.png
05-assignment-management.png
06-history.png
07-emergency-details-dialog.png
08-reports.png
09-critical-auto-assignment.png
10-persistence-after-restart.png
```

In Markdown:

```markdown
![Dashboard](screenshots/02-dashboard.png)
```

---

# 70. Final Submission Structure

Recommended source submission:

```text
EmergencyManagementSystem/
|
+-- src/
+-- README.md
+-- README.txt
+-- .gitignore
+-- screenshots/       # optional
+-- release/           # optional if distributing JAR/EXE separately
```

Do not commit unnecessary IDE/build cache files.

---

# 71. Final Viva Summary

If the teacher asks for a one-minute project explanation:

> Emergency Management System is a Java 26 Swing desktop application for admin-based emergency, response-team and assignment management. The application uses a separated Model, Manager, UI and Persistence architecture. Emergencies, teams and assignments are stored in ArrayLists and persisted using Java Serialization. The project demonstrates encapsulation through private fields and getters/setters, inheritance and polymorphism through the abstract ResponseTeam hierarchy, and abstraction through the ResponseTeam abstract class. The GUI uses JFrame, JTabbedPane, JTable, JOptionPane, JDialog, JComboBox, JCheckBox, JSpinner, JScrollPane and CardLayout. A critical emergency can automatically receive a suitable available response team, which changes the emergency to ASSIGNED and the team to BUSY. History and Reports use the same shared manager data, and the application can reload persistent data after restart.

---

# 72. Final Principle

The most important thing for the viva is not memorizing the README.

Be able to trace a real feature:

```text
Button click
   |
   v
UI method
   |
   v
Manager method
   |
   v
Model / ArrayList
   |
   v
FileManager
   |
   v
.dat file
   |
   v
JTable refresh
```

That is the core logic of the application.

---

# END OF README
