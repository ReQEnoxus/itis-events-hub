CREATE TABLE vol_user(
  first_name varchar[20],
  last_name varchar[20],
  patronymic varchar[20],
  points integer,
  login varchar[20] primary key,
  password varchar[20],
  role varchar[10]
);

CREATE TABLE vol_event(
  id integer primary key autoincrement,
  name varchar[20],
  description varchar[255],
  prize integer,
  capacity integer,
  hostlogin varchar[20] references vol_user(login),
  active boolean,
  place varchar[20],
  timestart varchar[20],
  timeend varchar[20],
  datestart varchar[20],
  dateend varchar[20]
);

CREATE TABLE user_event(
  userlogin varchar[20] references vol_user(login),
  eventid integer references vol_event(id)

);

CREATE TABLE event_user (
  eventid integer references vol_event(id),
  userlogin varchar[20] references vol_user
);

drop table event_user;
drop table user_event;
drop table vol_event;
drop table vol_user;

DELETE FROM event_user;
DELETE FROM user_event;
DELETE FROM vol_user;
DELETE FROM vol_event;