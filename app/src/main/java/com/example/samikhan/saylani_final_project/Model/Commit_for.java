package com.example.samikhan.saylani_final_project.Model;

/**
 * Created by Sami Khan on 2/26/2017.
 */

public class Commit_for {
        String comit_nmae;
        String commit_text;

        public Commit_for() {
        }

        public Commit_for(String comit_nmae, String commit_text) {
            this.comit_nmae = comit_nmae;
            this.commit_text = commit_text;
        }

        public String getComit_nmae() {
            return comit_nmae;
        }

        public void setComit_nmae(String comit_nmae) {
            this.comit_nmae = comit_nmae;
        }

        public String getCommit_text() {
            return commit_text;
        }

        public void setCommit_text(String commit_text) {
            this.commit_text = commit_text;
        }
}
