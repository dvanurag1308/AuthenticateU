namespace AuthenticatingU
{
    partial class WebPageForm
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.wb_navigateSite = new System.Windows.Forms.WebBrowser();
            this.SuspendLayout();
            // 
            // wb_navigateSite
            // 
            this.wb_navigateSite.Dock = System.Windows.Forms.DockStyle.Fill;
            this.wb_navigateSite.Location = new System.Drawing.Point(0, 0);
            this.wb_navigateSite.MinimumSize = new System.Drawing.Size(20, 20);
            this.wb_navigateSite.Name = "wb_navigateSite";
            this.wb_navigateSite.Size = new System.Drawing.Size(780, 412);
            this.wb_navigateSite.TabIndex = 0;
            this.wb_navigateSite.DocumentCompleted += new System.Windows.Forms.WebBrowserDocumentCompletedEventHandler(this.wb_navigateSite_DocumentCompleted);
            // 
            // WebPageForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(780, 412);
            this.Controls.Add(this.wb_navigateSite);
            this.Name = "WebPageForm";
            this.Text = "WebPage";
            this.WindowState = System.Windows.Forms.FormWindowState.Maximized;
            this.Load += new System.EventHandler(this.WebPageForm_Load);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.WebBrowser wb_navigateSite;
    }
}