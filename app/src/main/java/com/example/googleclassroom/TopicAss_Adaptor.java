package com.example.googleclassroom;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TopicAss_Adaptor extends RecyclerView.Adapter<TopicAss_Adaptor.FirstViewHolder>
{
    User thisUser ;
    Class thisClass ;
    ArrayList<Topic> topics ;
    ClassworkFragment activity ;
    Boolean isTeacher =false;

    public TopicAss_Adaptor(Class myclass , User user , ArrayList<Topic> topics, ClassworkFragment activity) {
        this.thisClass = myclass ;
        this.topics = topics;
        this.thisUser = user ;
        this.activity = activity ;

    }
public static class FirstViewHolder extends RecyclerView.ViewHolder {
    RecyclerView childRec;
    TextView topicTitle ;
    ImageButton dotsTopic ;
    PopupMenu topicPop;
    Menu menupop;
    public FirstViewHolder(View itemView) {
        super(itemView);
        childRec = itemView.findViewById(R.id.topic_parent);
        topicTitle = itemView.findViewById(R.id.topic_title_classwork);
        dotsTopic = itemView.findViewById(R.id.dots_topic_classwork);
    }
}
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public FirstViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.topic_parent_card, viewGroup, false);
        FirstViewHolder pvh = new FirstViewHolder(v) ;

        return pvh;
    }

    @Override
    public void onBindViewHolder(final FirstViewHolder viewHolder, int i) {

        Topic topictemp = topics.get(i) ;
        viewHolder.topicTitle.setText(topictemp.topicname);
        isTeacher = thisClass.findTeacher(thisUser);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(viewHolder.itemView.getContext());
        viewHolder.childRec.setLayoutManager(linearLayoutManager);
        Assign_Adaptor adapterChild = new Assign_Adaptor(topictemp.assignments , isTeacher);
        viewHolder.childRec.setAdapter(adapterChild);
        if (!isTeacher) {
            viewHolder.dotsTopic.setVisibility(View.INVISIBLE);
        }
        viewHolder.topicPop = new PopupMenu(activity.getContext(),viewHolder.dotsTopic);
        viewHolder.menupop = viewHolder.topicPop.getMenu();
        viewHolder.menupop.add(0, 0, 0, "Remove");
        viewHolder.menupop.add(0, 0, 0, "Edit");
        viewHolder.dotsTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.topicPop.show();
            }
        });
        viewHolder.topicPop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
               if (item.getTitle().equals("Remove"))
               {
                   //ToDo remove topic(topictemp) and its assingments
               }
                if (item.getTitle().equals("Edit"))
                {
                    //ToDO dialog for topic change
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return topics.size();
    }

//                if (id == R.id.edit_card) {
//                    final AlertDialog.Builder builder = new AlertDialog.Builder(activity.getContext());
//                    LayoutInflater inflater = activity.requireActivity().getLayoutInflater();
//                    View v = inflater.inflate(R.layout.dialog_create_topic, null);
//                    final EditText topicname =  v.findViewById(R.id.ctopic_name);
//                    topicname.setText(topic.name);
//                    builder.setView(v).setPositiveButton("Edit", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            if (topicname.length()!=0) {
//                                new Thread() {
//                                    @Override
//                                    public void run() {
//                                        super.run();
//                                        try {
//                                            Socket s = new Socket(activity.getResources().getString(R.string.ip), 8080);
//                                            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
//                                            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
//
//                                            System.out.println("Hello");
//                                            String[] a = {"EditTopic", myclass.code ,topic.name ,   topicname.getText().toString()};
//                                            System.out.println(a[1] +" "+ a[2]);
//                                            oos.writeObject(a);
//                                            oos.flush();
//
//
//
//                                            oos.close();
//                                            ois.close();
//                                            s.close();
//                                            RefreshCLW refreshCLW = new RefreshCLW(activity);
//                                            refreshCLW.execute("RefreshCLW", user.username, user.password, myclass.code);
//
//                                        } catch (Exception e) {
//                                            e.printStackTrace();
//                                        }
//                                    }
//                                }.start();
//                            }
//                            else
//                                Toast.makeText(activity.getContext(), "Topic Name is Empty", Toast.LENGTH_SHORT).show();
//                        }
//                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    });
//
//
//                    AlertDialog dialog = builder.create();
//                    dialog.show();
//
//                }
//                return false;
//            }
//        });
//        popup.show();
//    }
//


}



class Assign_Adaptor extends RecyclerView.Adapter<Assign_Adaptor.SecondViewHolder> {
    boolean isTeacher ;
    ArrayList<Assignment> assignments ;

    Assign_Adaptor(ArrayList<Assignment> assignments , boolean is) {
        this.assignments = assignments ;
        this.isTeacher = is ;
    }

    public static class SecondViewHolder extends RecyclerView.ViewHolder {
        ImageButton dots ;
        TextView assignTitle;
        TextView assignTime;
        public SecondViewHolder(View view) {
            super(view);
            dots = view.findViewById(R.id.dots_assign_adaptor);
            assignTime = view.findViewById(R.id.assign_time_adaptor);
            assignTitle = view.findViewById(R.id.assign_title_adaptor);
        }
    }

    @Override
    public void onBindViewHolder( SecondViewHolder viewHolder, int i) {
        Assignment tempass = assignments.get(i);
        viewHolder.assignTime.setText(tempass.time);
        viewHolder.assignTitle.setText(tempass.name);
        if (!isTeacher) {
            viewHolder.dots.setVisibility(View.INVISIBLE);
        }

    }
    @Override
    public SecondViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.assignment_child_card, viewGroup, false);
        SecondViewHolder SVH= new SecondViewHolder(v) ;
        return SVH;
    }

    @Override
    public int getItemCount() {
        return assignments.size();
    }
}
