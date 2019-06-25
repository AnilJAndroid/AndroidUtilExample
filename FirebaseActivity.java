package com.flyer.supportapp.activities.Chat;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.flyer.supportapp.R;
import com.flyer.supportapp.Utility.Feature;
import com.flyer.supportapp.Utility.PreferencesKt;
import com.flyer.supportapp.activities.Chat.shopdatabase.*;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.*;

import java.util.*;

import static com.flyer.supportapp.activities.Chat.shopdatabase.DbMessage.FIELD_TIME_STAMP;


public class FirebaseShopActivity extends AppCompatActivity {

    protected String DB_FIELD_VALUE_IMAGE = "Image";
    protected String channelParentId = "";
    protected FirebaseFirestore mDatabase;
    protected List<DbChannels> listDbChannels = new ArrayList<>();
    protected ArrayList<DbChannels> tempList = new ArrayList<>();
    protected boolean isCurrentUserTyping = false;
    protected ArrayList<DbMessageParent> listDbMessage = new ArrayList<>();
    private String DB_TABLE_USER = "Users";
    private String DB_TABLE_MESSAGE = "Messages";
    private String DB_TABLE_CHANNELS = "channel_support";
    private String DB_TABLE_TYPE_INDICATOR = "typeIndicator";
    private Context context;
    private Feature features;
    protected String user_id, userName, userProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDatabase = FirebaseFirestore.getInstance();

        user_id = PreferencesKt.getUserId();
        userName = PreferencesKt.getFirstName();
        userProfile = PreferencesKt.getProfilePic();

        features = new Feature(getApplicationContext());

    }

    protected void initContext(Context context) {
        this.context = context;
    }


    protected void addNewUser(DbUserProfile dbUserProfile, OnCompleteListener onUserAddComplete) {
        mDatabase.collection(DB_TABLE_USER)
                .add(dbUserProfile)
                .addOnCompleteListener(this, onUserAddComplete);
    }

    protected void addNewChannel(DbChannels dbChannels, OnCompleteListener onChannelAddComplete) throws Exception {
        mDatabase.collection(DB_TABLE_CHANNELS)
                .document(dbChannels.getChannelId())
                .set(dbChannels)
                .addOnCompleteListener(this, onChannelAddComplete)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("FindError", "REDIRECT");
                    }
                });
    }

    protected void addNewChatMessage(DbChannels dbChannels, DbMessage dbMessage, OnCompleteListener onMessageAddComplete) {
        mDatabase.collection(DB_TABLE_CHANNELS)
                .document(dbChannels.getChannelDocumentId())
                .collection(DB_TABLE_MESSAGE)
                .add(dbMessage)
                .addOnCompleteListener(this, onMessageAddComplete);
    }


    protected void addNewChatIndicator(DbChannels dbChannels, String uid, DbTypeIndicator dbTypeIndicator, OnCompleteListener onCompleteListener) {
        mDatabase.collection(DB_TABLE_CHANNELS)
                .document(dbChannels.getChannelDocumentId())
                .collection(DB_TABLE_TYPE_INDICATOR)
                .document(uid)
                .set(dbTypeIndicator)
                .addOnCompleteListener(this, onCompleteListener)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("FindError", "REDIRECT");

                    }
                });
    }


    protected void queryChannel(String fieldKey, String fieldValue, OnCompleteListener<QuerySnapshot> onCompleteListener) {
        mDatabase.collection(DB_TABLE_CHANNELS)
                .whereEqualTo(fieldKey, fieldValue)
                .get()
                .addOnCompleteListener(onCompleteListener)
                .addOnFailureListener(e -> Log.e("FindError", "CHANNEL QUERY=" + e.toString()));
    }


    protected void queryUsersChannel(ArrayList<String> fieldKey, ArrayList<String> fieldValue, OnCompleteListener<QuerySnapshot> onCompleteListener) {
        mDatabase.collection(DB_TABLE_CHANNELS)
                .whereEqualTo(fieldKey.get(0), fieldValue.get(0))
                .whereEqualTo(fieldKey.get(1), fieldValue.get(1))
                .get()
                .addOnCompleteListener(onCompleteListener);
    }

    protected void queryUser(String fieldKey, String fieldValue, OnCompleteListener onUserAddComplete) {
        mDatabase.collection(DB_TABLE_USER)
                .whereEqualTo(fieldKey, fieldValue)
                .get()
                .addOnCompleteListener(this, onUserAddComplete);
    }

    protected void queryChat(DbChannels dbChannels, OnCompleteListener<QuerySnapshot> onCompleteListener) {
        mDatabase.collection(DB_TABLE_CHANNELS)
                .document(dbChannels.getChannelDocumentId())
                .collection(DB_TABLE_MESSAGE)
                .orderBy(FIELD_TIME_STAMP, Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(onCompleteListener);
    }

    protected ListenerRegistration queryChatUpdateListner(DbChannels dbChannels, String authId, EventListener<QuerySnapshot> onCompleteListener) {
        CollectionReference docRef = mDatabase.collection(DB_TABLE_CHANNELS)
                .document(dbChannels.getChannelDocumentId())
                .collection(DB_TABLE_MESSAGE);

        ListenerRegistration registration = docRef.orderBy(FIELD_TIME_STAMP, Query.Direction.DESCENDING).addSnapshotListener(onCompleteListener);
        return registration;
    }


    protected ListenerRegistration queryChatIndicatorUpdateListner(DbChannels dbChannels, EventListener<QuerySnapshot> onCompleteListener) {
        CollectionReference docRef = mDatabase.collection(DB_TABLE_CHANNELS)
                .document(dbChannels.getChannelDocumentId())
                .collection(DB_TABLE_TYPE_INDICATOR);

        ListenerRegistration registration = docRef.addSnapshotListener(onCompleteListener);
        return registration;
    }

    protected void queryChatIndicator(DbChannels dbChannels, OnCompleteListener<QuerySnapshot> onCompleteListener) {
        mDatabase.collection(DB_TABLE_CHANNELS)
                .document(dbChannels.getChannelDocumentId())
                .collection(DB_TABLE_TYPE_INDICATOR)
                //.whereEqualTo(fieldKey, fieldValue)
                //.whereEqualTo(DbChannels.FIELD_USER_2, mUserId)
                .get()
                .addOnCompleteListener(onCompleteListener);
    }

    protected void queryChatIndicatorRemove(DbChannels dbChannels, OnCompleteListener<QuerySnapshot> onCompleteListener) {
        mDatabase.collection(DB_TABLE_CHANNELS)
                .document(dbChannels.getChannelDocumentId())
                .collection(DB_TABLE_TYPE_INDICATOR)
                //.whereEqualTo(fieldKey, fieldValue)
                //.whereEqualTo(DbChannels.FIELD_USER_2, mUserId)
                .get()
                .addOnCompleteListener(onCompleteListener);
    }

    protected void queryChannelUpdate(DbChannels dbChannels, OnCompleteListener onCompleteListener) {
        mDatabase.collection(DB_TABLE_CHANNELS)
                .document(dbChannels.getChannelDocumentId())
                .set(dbChannels)
                .addOnCompleteListener(onCompleteListener);
    }


    protected void queryDeleteChat(DbChannels dbChannels, OnSuccessListener onSuccessListener) {
        mDatabase.collection(DB_TABLE_CHANNELS)
                .document(dbChannels.getChannelDocumentId())
                .delete()
                .addOnSuccessListener(onSuccessListener);
    }

    /*protected void setChannelResultList(Task<QuerySnapshot> task) {
        for (DocumentSnapshot document : task.getResult()) {
            Log.d(TAG, document.getId() + " => " + document.getDataItems());
            DbChannels dbChannels = new DbChannels();

            dbChannels = getDbChannel(dbChannels, document);
            tempList.add(dbChannels);
        }
    }*/

    protected DbChannels getDbChannel(DbChannels dbChannels, DocumentSnapshot document) {
        dbChannels.setChannelDocumentId(document.getId());
        dbChannels.setChannelId(document.getString(DbChannels.FIELD_CHANNEL_ID));
        dbChannels.setName_User1(document.getString(DbChannels.FIELD_NAME_USER_1));
        dbChannels.setName_User2(document.getString(DbChannels.FIELD_NAME_USER_2));
        dbChannels.setProfile_User1(document.getString(DbChannels.FIELD_PROFILE_USER_1));
        dbChannels.setProfile_User2(document.getString(DbChannels.FIELD_PROFILE_USER_2));
        dbChannels.setUser1(document.getString(DbChannels.FIELD_USER_1));
        dbChannels.setUser2(document.getString(DbChannels.FIELD_USER_2));
        dbChannels.setLastMessage(document.getString(DbChannels.FIELD_LAST_MESSAGE));
        dbChannels.setTimestamp(document.getTimestamp(DbChannels.FIELD_MESSAGE_TIME_STAMP));

//        Timestamp timestamp = null;
//        try {
//            timestamp = document.getTimestamp(DbChannels.FIELD_MESSAGE_TIME_STAMP);
//        } catch (Exception e) { e.printStackTrace(); }
//
//        // If exception occur
//        if (timestamp == null) {
//            timestamp = new Timestamp(new Date(0));
//        }
//        dbChannels.setTimestamp(timestamp);
        return dbChannels;
    }

    protected void setChatList(Task<QuerySnapshot> task) {
        for (DocumentSnapshot document : task.getResult()) {
            DbMessageParent dbMessage = getChatmessage(document);
            listDbMessage.add(dbMessage);
        }
    }

    protected DbMessageParent getChatmessage(DocumentSnapshot document) {

        Log.d("", document.getId() + " => " + document.getData());
        boolean containsTime = document.contains(FIELD_TIME_STAMP);
        Log.e("FirebaseShopActivity", String.format("containsTime is %s", containsTime));
        Timestamp timeStamp = null;
        if (containsTime) {
            Object o = document.get(FIELD_TIME_STAMP);
            if (o instanceof Timestamp) {
                timeStamp = (Timestamp) o;
            }
        }
        if (timeStamp == null) {
            timeStamp = new Timestamp(new Date());
        }
        DbMessageParent dbMessageParent = new DbMessageParent();
        dbMessageParent.setMsgId(document.getId());
        DbMessage dbMessage = new DbMessage();
        dbMessage.setSenderUserId(document.getString(DbMessage.FIELD_FIREBASE_SENDER_ID));
        dbMessage.setSenderName(document.getString(DbMessage.FIELD_SENDER_NAME));
        dbMessage.setReceiverUserId(document.getString(DbMessage.FIELD_RECIEVER_ID));
        dbMessage.setReceiverName(document.getString(DbMessage.FIELD_RECIEVER_NAME));
        dbMessage.setText(document.getString(DbMessage.FIELD_MESSGAE));
        dbMessage.setLatitude(document.getString(DbMessage.FIELD_LAT));
        dbMessage.setLongitude(document.getString(DbMessage.FIELD_LONG));
        dbMessage.setChatImage(document.getString(DbMessage.FIELD_CHAT_IMAGE));
        dbMessage.setTimestamp(timeStamp);
        dbMessageParent.setDbMessage(dbMessage);
        return dbMessageParent;
    }

    protected String returnChannelId(String friendUserId, String currentUserId, String orderId) {
        try {
            int friendId = Integer.parseInt(friendUserId);
            int myId = Integer.parseInt(currentUserId);

            String minId = (friendId > myId) ? currentUserId : friendUserId;
            String maxId = (friendId < myId) ? currentUserId : friendUserId;

            return minId + "_" + maxId + "_" + orderId;
        } catch (Exception e) {
            return null;
        }
    }

    protected void toggleIndicator(DbChannels dbChannels) {
        DbTypeIndicator dbTypeIndicator = new DbTypeIndicator();
        dbTypeIndicator.setUserTyping(isCurrentUserTyping);
        addNewChatIndicator(dbChannels, user_id, dbTypeIndicator, new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()) {
                    DocumentReference documentReference = (DocumentReference) task.getResult();
                    Log.e("FindError", "REDIRECT");
                } else {
                    Log.e("FindError", "REDIRECT");
                }
            }
        });
    }

    protected void showError() {
        String errorMessage = getResources().getString(R.string.txt_something_went_wrong);
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
    }

    protected void checkChannel(String support_id, final String otherUserId, final String otherUserName, final String otherUserImage) {
        final String channelId = features.returnChannelId(otherUserId, user_id, support_id);
//        here we user order_id instread of channelId
        queryChannel(DbChannels.FIELD_CHANNEL_ID, channelId, task -> {
            if (task.isSuccessful()) {
                DbChannels dbChannels = null;
                for (DocumentSnapshot document : task.getResult()) {
                    dbChannels = new DbChannels();
                    dbChannels = getDbChannel(dbChannels, document);
                    break;
                }
                if (dbChannels == null) {
                    //CreateChannel
                    dbChannels = new DbChannels();
                    dbChannels.setChannelDocumentId(channelId);
                    dbChannels.setChannelId(channelId);
                    dbChannels.setUser1(user_id);
                    dbChannels.setUser2(otherUserId);
                    dbChannels.setName_User1(otherUserName);
                    dbChannels.setName_User2(userName);
                    dbChannels.setProfile_User1(otherUserImage);
                    dbChannels.setProfile_User2(userProfile);
//                    dbChannels.setTimestamp(new Timestamp(new Date()));
                    createChannel(dbChannels);
                } else {
                    ChatChannelCreated chatChannelCreated = (ChatChannelCreated) context;
                    chatChannelCreated.onChannelFound(dbChannels);
                }
            } else {
                showError();
                Log.d("test", "Error getting documents: ", task.getException());
            }
        });
    }

    protected void createChannel(final DbChannels addDbChannel) {
        try {
            addNewChannel(addDbChannel, (OnCompleteListener<Void>) task -> {
                if (task.isSuccessful()) {
                    ChatChannelCreated chatChannelCreated = (ChatChannelCreated) context;
                    chatChannelCreated.onChannelCreated(addDbChannel, null);
                } else {
                    showError();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
//            createChannel(addDbChannel);
        }
    }

    /*protected void gotoChat(DbChannels dbChannels, int position) {
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra(Constants.PASS_CHAT_CHANNEL, dbChannels);
        intent.putExtra(Constants.PASS_POSITION, position);
        startActivityForResult(intent, Constants.PASSING_CHAT_CHANNEL);
    }*/

    protected void queryChannelUpdateChatCountLastMessage(DbChannels dbChannels, OnCompleteListener<Void> onCompleteListener) {
        DocumentReference str = mDatabase.collection(DB_TABLE_CHANNELS)
                .document(dbChannels.getChannelDocumentId());
        /*if (mUserId.equalsIgnoreCase(dbChannels.getUser1())) {
            str.update(DbChannels.FIELD_USER_2_MSG_COUNT, dbChannels.getUser2_msgCnt());
        } else {
            str.update(DbChannels.FIELD_USER_1_MSG_COUNT, dbChannels.getUser1_msgCnt());
        }*/

        str.update(DbChannels.FIELD_MESSAGE_TIME_STAMP, dbChannels.getTimestamp());
        Map<String, Object> updates = new HashMap<>();
       /* if (mUserId.equalsIgnoreCase(dbChannels.getUser1())) {
            updates.put(DbChannels.FIELD_USER_2_MSG_COUNT, dbChannels.getUser2_msgCnt());
        } else {
            updates.put(DbChannels.FIELD_USER_1_MSG_COUNT, dbChannels.getUser1_msgCnt());
        }*/
        updates.put(DbChannels.FIELD_MESSAGE_TIME_STAMP, FieldValue.serverTimestamp());
        updates.put(DbChannels.FIELD_LAST_MESSAGE, dbChannels.getLastMessage());
        str.update(updates).addOnCompleteListener(onCompleteListener)
                .addOnFailureListener(e -> Log.e("FindError", "Error=" + e.toString()));
    }

    protected void update_channel_image(String user_id, String username, String userProfile) {
//        Log.e(TAG, "user_id " + user_id);
        try {
            mDatabase.collection(DB_TABLE_CHANNELS).whereEqualTo(DbChannels.FIELD_USER_1, user_id).addSnapshotListener((queryDocumentSnapshots, e) -> {
                if (e != null) {
//                    Log.w(TAG, "listen:error", e);
                    return;
                }
                List<DocumentSnapshot> documentSnapshots = queryDocumentSnapshots.getDocuments();

//                Log.e("field1 ",documentSnapshots.size()+"");

                for (DocumentSnapshot documentSnapshot : documentSnapshots) {
                    if (documentSnapshot.exists()) {
//                        Log.e(TAG, documentSnapshot.getDataItems().toString());
                        DocumentReference str = mDatabase.collection(DB_TABLE_CHANNELS).document(documentSnapshot.getReference().getId());

                        Map<String, Object> updates = new HashMap<>();
                        updates.put(DbChannels.FIELD_NAME_USER_1, username);
                        updates.put(DbChannels.FIELD_PROFILE_USER_1, userProfile);
                        str.update(updates).addOnSuccessListener(aVoid -> {
//                            Log.e(TAG, "Success=");
                        }).addOnFailureListener(e1 -> {
//                            Log.e("FindError", "Error=" + e1.toString());
                        });
                    }
                }
            });

            mDatabase.collection(DB_TABLE_CHANNELS).whereEqualTo(DbChannels.FIELD_USER_2, user_id).addSnapshotListener((queryDocumentSnapshots, e) -> {
                if (e != null) {
                    //  Log.w(TAG, "listen:error", e);
                    return;
                }
                List<DocumentSnapshot> documentSnapshots = queryDocumentSnapshots.getDocuments();
//                Log.e("field2 ",documentSnapshots.size()+"");
                for (DocumentSnapshot documentSnapshot : documentSnapshots) {
                    if (documentSnapshot.exists()) {
                        Log.e("", documentSnapshot.getData().toString());
                        DocumentReference str = mDatabase.collection(DB_TABLE_CHANNELS).document(documentSnapshot.getReference().getId());

                        Map<String, Object> updates = new HashMap<>();
                        updates.put(DbChannels.FIELD_NAME_USER_2, username);
                        updates.put(DbChannels.FIELD_PROFILE_USER_2, userProfile);
                        str.update(updates).addOnSuccessListener(aVoid -> {
//                            Log.e(TAG, "Success=");
                        }).addOnFailureListener(e1 -> {
//                            Log.e("FindError", "Error=" + e1.toString());
                        });
                    }
                }
            });
        } catch (Exception e) {
            Log.e("FindError", "Error=" + e.toString());
        }

    }


    protected void addNewChatMessagePrepare(DbChannels dbChannels, DbMessage dbMessage, OnCompleteListener<DocumentReference> onMessageAddComplete) {
        getFirebaseRealTimeTime(dbChannels, dbMessage, onMessageAddComplete);
    }


    public void getFirebaseRealTimeTime(final DbChannels dbChannels, final DbMessage dbMessage,
                                        final OnCompleteListener<DocumentReference> onMessageAddComplete) {

        addNewChatMessage(dbChannels, dbMessage, onMessageAddComplete);
    }

}
