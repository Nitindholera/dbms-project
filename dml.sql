-- max of friend id from friend
select max(friend_id) from friend;

-- max of chat id in chat
select max(chat_id) from chat;

-- find friend id and chat id from username1 and username2
select a.Friend_id, c.Chat_id
from is_member_friend as a, is_member_friend as b, friend as c
where a.User_name = "user1" and b.User_name = "user2" and a.Friend_id = b.Friend_id and c.Friend_id = a.Friend_id