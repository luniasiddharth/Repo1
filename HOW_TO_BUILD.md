# Building the APK on GitHub (no local Android setup needed)

This project is now build-ready: it has the Gradle wrapper (`gradlew`) and a
GitHub Actions workflow (`.github/workflows/build-apk.yml`) that compiles a
debug APK automatically on GitHub's own servers whenever this code is pushed.

I don't have write access to your GitHub repo from this session, so you'll
need to upload these files yourself — no command line required:

1. Go to https://github.com/luniasiddharth/Repo1
2. Click **Add file → Upload files**.
3. Drag in *everything* from this unzipped folder, keeping the folder
   structure intact (including the hidden `.github` folder — if your file
   picker hides dotfolders, use "Add file → Upload files" and drag the whole
   unzipped project folder in one go, or use `git` locally: `git add -A`,
   `git commit -m "add project"`, `git push`).
4. Commit the upload directly to the `main` branch.
5. Click the **Actions** tab at the top of the repo. You should see a
   workflow run called "Build debug APK" start automatically (takes ~2-3
   minutes).
6. Once it finishes with a green check, click into that run, scroll to
   **Artifacts**, and download **sids-reminders-debug-apk**. Unzip it — the
   `.apk` file inside is what you install on your phone.

## Installing on your phone

This is a **debug** build, self-signed for testing (not a Play Store
release). To install it:

1. Copy the `.apk` file to your phone (e.g. via a link, email, or Google
   Drive to yourself).
2. On your phone, tap the file. Android will ask you to allow installs from
   this source (Settings → Apps → Special access → Install unknown apps) —
   allow it for the app you used to open the file.
3. Tap Install.

## What's in this build

This is currently the UI skeleton from your original upload (add/search/delete
reminders in memory). It does **not** yet persist reminders across restarts,
schedule the actual 7-day/3-day/same-day notifications, or support XLSX
import/export — those were flagged as "not yet implemented" in the original
README. Let me know if you'd like those built out next; I can add them and
this same GitHub Actions workflow will rebuild the APK automatically.
