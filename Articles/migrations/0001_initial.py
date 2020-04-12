# Generated by Django 3.0.4 on 2020-03-31 19:00

from django.conf import settings
from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    initial = True

    dependencies = [
        migrations.swappable_dependency(settings.AUTH_USER_MODEL),
    ]

    operations = [
        migrations.CreateModel(
            name='article',
            fields=[
                ('idArticle', models.AutoField(editable=False, primary_key=True, serialize=False)),
                ('dateAr', models.DateTimeField(auto_now=True, verbose_name='date de redaction')),
                ('contenuAr', models.TextField(verbose_name='Contenu')),
                ('validerAR', models.BooleanField(blank=True, default=False, null=True, verbose_name='Validé')),
                ('refuserAR', models.BooleanField(blank=True, default=False, null=True, verbose_name='refusé')),
                ('idModerateurAr', models.ForeignKey(blank=True, null=True, on_delete=django.db.models.deletion.CASCADE, related_name='art_moderateur', to=settings.AUTH_USER_MODEL, verbose_name='moderateur')),
                ('idRedacteurAr', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, related_name='art_redacteur', to=settings.AUTH_USER_MODEL, verbose_name='redacteur')),
            ],
        ),
        migrations.CreateModel(
            name='videoThematique',
            fields=[
                ('idVideoThema', models.AutoField(primary_key=True, serialize=False)),
                ('lienVthema', models.CharField(max_length=255, verbose_name='lien')),
                ('validerVthema', models.BooleanField(default=False, verbose_name='validée')),
                ('refuserVthema', models.BooleanField(default=False, verbose_name='refusée')),
                ('titreVthema', models.CharField(max_length=255, verbose_name='titre')),
                ('descriptionVthema', models.TextField(verbose_name='description')),
                ('idModerateurVthema', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, related_name='vid_moderateur', to=settings.AUTH_USER_MODEL, verbose_name='moderateur')),
                ('idUtilisateurVThema', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, related_name='vid_utilisateur', to=settings.AUTH_USER_MODEL, verbose_name='utilisateur')),
            ],
        ),
        migrations.CreateModel(
            name='videoArticle',
            fields=[
                ('idVideo', models.AutoField(editable=False, primary_key=True, serialize=False)),
                ('lienViAc', models.CharField(max_length=255, verbose_name='lien')),
                ('idArticleVd', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='Articles.article', verbose_name='article')),
            ],
        ),
        migrations.CreateModel(
            name='photoArticle',
            fields=[
                ('idPhoto', models.AutoField(editable=False, primary_key=True, serialize=False)),
                ('lienPhAc', models.CharField(max_length=255, verbose_name='lien')),
                ('idArticlePh', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='Articles.article', verbose_name='article')),
            ],
        ),
        migrations.CreateModel(
            name='commentaire',
            fields=[
                ('idCommentaire', models.AutoField(editable=False, primary_key=True, serialize=False)),
                ('contenuCom', models.TextField(verbose_name='contenu')),
                ('signalerCom', models.BooleanField(blank=True, default=False, null=True, verbose_name='signalé')),
                ('idArticleCom', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='Articles.article', verbose_name='article')),
                ('idModerateurCom', models.ForeignKey(blank=True, null=True, on_delete=django.db.models.deletion.CASCADE, related_name='com_moderateur', to=settings.AUTH_USER_MODEL, verbose_name='moderateur')),
                ('idUtilisateurCom', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, related_name='com_utilisateur', to=settings.AUTH_USER_MODEL, verbose_name='utilisateur')),
            ],
        ),
    ]
